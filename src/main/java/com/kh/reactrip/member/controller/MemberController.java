package com.kh.reactrip.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.member.model.dto.SignupRequest;
import com.kh.reactrip.common.ResponseData;
import com.kh.reactrip.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@PostMapping("/signup")
	public ResponseEntity<ResponseData<Object>> signUp(@RequestBody SignupRequest request) {
		
		memberService.signUp(request);
		return ResponseData.ok("회원가입 성공");
		
	}

//	@PutMapping("/profile/image/{memberId}")
//	public ResponseEntity<ResponseData<Object>> updateProfileImage(@PathVariable Long memberId,
//																   @RequestParam MultipartFile profileImage) {
//		
//		memberService.updateProfileImage(memberId, profileImage);
//		return ResponseData.ok("프로필 이미지 변경 성공");
//	}
}
