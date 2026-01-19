package com.kh.reactrip.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.reactrip.exception.CustomAuthenticationException;
import com.kh.reactrip.token.model.dao.TokenMapper;
import com.kh.reactrip.token.model.util.JwtUtil;
import com.kh.reactrip.token.model.vo.RefreshToken;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
	
	private final JwtUtil tokenUtil;
	private final TokenMapper tokenMapper;
	/**
	 * 토큰 생성 및 저장
	 * @param username 사용자 ID
	 * @param userNo 사용자 번호
	 * @return AccessToken, RefreshToken을 담은 Map
	 */
	@Transactional
	public Map<String, String> generateToken(String username, Long authNo, String role) {
		// 1. Access Token, Refresh Token 생성
		Map<String, String> tokens = createTokens(username, role);
		
		// 2. Refresh Token DB에 저장
		saveToken(tokens.get("refreshToken"), authNo);
		
		return tokens;
	}
	
	/**
	 * Access Token과 Refresh Token 생성
	 */
	private Map<String, String> createTokens(String username, String role) {
		String accessToken = tokenUtil.getAccessToken(username, role);
		String refreshToken = tokenUtil.getRefreshToken(username);
		
		log.info("엑세스 토큰 : {}", accessToken);
		log.info("리프레시 토큰 : {}", refreshToken);
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		
		return tokens;
	}
	
	/**
	 * Refresh Token을 DB에 저장
	 * 기존 토큰이 있으면 삭제 후 저장
	 */
	@Transactional
	private void saveToken(String refreshToken, Long authNo) {
		// Refresh Token 만료 시간 계산 (7일)
		
		
		RefreshToken token = RefreshToken.builder()
				.token(refreshToken)
				.authNo(authNo) 
				.expiration(System.currentTimeMillis() + 36000000L * 72) 
				.build();
		log.info("token: {} ", token);
		// 기존 토큰 삭제 후 새 토큰 저장
		try {
			tokenMapper.deleteTokenByUserNo(authNo);
		} catch (Exception e) {
			log.debug("기존 토큰 없음 (정상)");
		}
		
		tokenMapper.saveToken(token);
		log.info("Refresh Token 저장 완료 - userNo: {}", authNo);
	}
	
	/**
	 * Refresh Token 검증 및 새 토큰 발급
	 * @param refreshToken 검증할 Refresh Token
	 * @return 새로 발급된 Access Token, Refresh Token
	 */
	@Transactional
	public Map<String, String> validateToken(String refreshToken) {
		// 1. DB에서 Refresh Token 조회
		RefreshToken token = tokenMapper.findByToken(refreshToken);
		
		// 2. 토큰이 없거나 만료되었으면 예외 발생
		if (token == null) {
			throw new CustomAuthenticationException("유효하지 않은 Refresh Token입니다.");
		}
		
		if (token.getExpiration() < System.currentTimeMillis()) {
			throw new CustomAuthenticationException("만료된 Refresh Token입니다.");
		}
		
		// 3. Refresh Token에서 사용자 정보 추출
		Claims claims = tokenUtil.parseJwt(refreshToken);
		String username = claims.getSubject();
		
		// 4. 새 토큰 생성 및 반환
		return createTokens(username, "ROLE_USER");
	}
}