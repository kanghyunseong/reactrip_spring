package com.kh.reactrip.token.model.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kh.reactrip.exception.CustomAuthenticationException;
import com.kh.reactrip.token.config.TokenProperties;
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
	private final TokenProperties tokenProperties;  // 추가
	
	// 상수 정의
	private static final String ACCESS_TOKEN_KEY = "accessToken";
	private static final String REFRESH_TOKEN_KEY = "refreshToken";
	private static final String DEFAULT_ROLE = "ROLE_USER";
	
	/**
	 * 토큰 생성 및 저장
	 * @param username 사용자 ID
	 * @param authNo 인증 번호
	 * @param role 권한
	 * @return AccessToken, RefreshToken을 담은 Map
	 */
	@Transactional
	public Map<String, String> generateToken(String username, Long authNo, String role) {
		// 1. Access Token, Refresh Token 생성
		Map<String, String> tokens = createTokens(username, role);
		
		// 2. Refresh Token DB에 저장
		saveToken(tokens.get(REFRESH_TOKEN_KEY), authNo);
		
		return tokens;
	}
	
	/**
	 * Access Token과 Refresh Token 생성
	 */
	private Map<String, String> createTokens(String username, String role) {
		String accessToken = tokenUtil.getAccessToken(username, role);
		String refreshToken = tokenUtil.getRefreshToken(username);
		
		log.info("엑세스 토큰 생성 완료");
		log.info("리프레시 토큰 생성 완료");
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put(ACCESS_TOKEN_KEY, accessToken);
		tokens.put(REFRESH_TOKEN_KEY, refreshToken);
		
		return tokens;
	}
	
	/**
	 * Refresh Token을 DB에 저장
	 * 기존 토큰이 있으면 삭제 후 저장
	 */
	@Transactional
	private void saveToken(String refreshToken, Long authNo) {
		// Refresh Token 만료 시간 계산 (7일)
		long expirationTime = 60L;
		RefreshToken token = RefreshToken.builder()
				.token(refreshToken)
				.authNo(authNo) 
				.expiration(expirationTime)
				.build();
		
		log.debug("RefreshToken 생성: authNo={}, expiration={}", authNo, expirationTime);
		
		// 기존 토큰 삭제 후 새 토큰 저장
		try {
			tokenMapper.deleteTokenByUserNo(authNo);
			log.debug("기존 Refresh Token 삭제 완료");
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
		
		// 2. 토큰 유효성 검증
		validateRefreshToken(token);
		
		// 3. Refresh Token에서 사용자 정보 추출
		Claims claims = tokenUtil.parseJwt(refreshToken);
		String username = claims.getSubject();
		
		// 4. 새 토큰 생성 및 반환
		return createTokens(username, DEFAULT_ROLE);
	}
	
	/**
	 * Refresh Token 유효성 검증
	 */
	private void validateRefreshToken(RefreshToken token) {
		if (token == null) {
			log.warn("유효하지 않은 Refresh Token 접근 시도");
			throw new CustomAuthenticationException("유효하지 않은 Refresh Token입니다.");
		}
		
		if (token.getExpiration() < System.currentTimeMillis()) {
			log.warn("만료된 Refresh Token 사용 시도 - authNo: {}", token.getAuthNo());
			throw new CustomAuthenticationException("만료된 Refresh Token입니다.");
		}
	}
}