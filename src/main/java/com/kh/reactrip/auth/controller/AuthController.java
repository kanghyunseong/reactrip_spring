package com.kh.reactrip.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.auth.model.service.AuthService;
import com.kh.reactrip.common.ResponseData;
import com.kh.reactrip.token.model.service.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	private final AuthService authService;
	private final TokenService tokenService;
	
	
	@PostMapping("/login")
	public ResponseEntity<ResponseData<Object>> login(@Valid @RequestBody MemberLoginDTO member) {
		Map<String, String> loginResponse = authService.login(member);
		return ResponseData.ok("로그인 성공");
	}

	@PostMapping("/logout")
	public ResponseEntity<ResponseData<Object>> logout(@Valid @RequestBody MemberLoginDTO member) {
		authService.logout(member);
		return ResponseData.ok("로그아웃 성공");
	}

	@PostMapping("/refresh")
	public ResponseEntity<ResponseData<Map<String, String>>> refresh(@RequestBody Map<String, String> token) {

		String refreshToken = token.get("refreshToken");
	

		return ResponseData.created(tokenService.validateToken(refreshToken));

	}

}
