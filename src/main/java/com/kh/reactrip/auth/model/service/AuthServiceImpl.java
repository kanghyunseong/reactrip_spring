package com.kh.reactrip.auth.model.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.exception.CustomAuthenticationException;
import com.kh.reactrip.exception.LogoutFailureException;
import com.kh.reactrip.member.model.vo.AuthMember;
import com.kh.reactrip.token.model.dao.TokenMapper;
import com.kh.reactrip.token.model.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	@Override
	public Map<String, String> login(MemberLoginDTO member) {

		CustomUserDetails user = getCustomUserDetails(member);

		//log.info("로그인성공! ");
		//log.info("인증에 성공한 사용자의 정보 : {} ", user);

		Map<String, String> loginResponse = getLoginResponse(user);
		return loginResponse;

	}

	private CustomUserDetails getCustomUserDetails(MemberLoginDTO member) {

		try {
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getMemberPwd()));

			return (CustomUserDetails) auth.getPrincipal();

		} catch (AuthenticationException e) {

			throw new CustomAuthenticationException("아이디 또는 비밀번호를 확인하세요.");
		}
	}

	private Map<String, String> getLoginResponse(CustomUserDetails user) {

		String role = user.getAuthorities().stream().findFirst().get().getAuthority();
		Map<String, String> loginResponse = tokenService.generateToken(user.getUsername(), user.getAuthNo(), role);
		loginResponse.put("userNo", String.valueOf(user.getMemberNo()));
		loginResponse.put("userId", user.getUsername());
		loginResponse.put("birthDay", user.getBirthDay());
		loginResponse.put("email", user.getEmail());
		loginResponse.put("phone", user.getPhone());
		loginResponse.put("role", user.getAuthorities().toString());
		Map<String, String> loginResponse = tokenService.generateToken(user.getUsername(), user.getMemberNo(), role);
		loginResponse.put("userNo", String.valueOf(user.getMemberNo()));
		loginResponse.put("userId", user.getUsername());
		loginResponse.put("birthDay", user.getBirthday());
		loginResponse.put("email", user.getEmail());
		loginResponse.put("phone", user.getPhone());
		loginResponse.put("role", user.getAuthorities().toString());
		return loginResponse;

	}

	@Override
	public void logout(@Valid MemberLoginDTO member) {
		
		int result = TokenMapper.deleteTokenForLogout(member);
		
		if(result == 1) {
			return;
		} else {
			throw new LogoutFailureException("로그아웃 오류 발생, 관리자에게 문의해주세요");
		}
	}

}
