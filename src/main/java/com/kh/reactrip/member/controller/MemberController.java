package com.kh.reactrip.member.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.member.model.dto.MemberDTO;
import com.kh.reactrip.member.model.dto.SignupRequest;
import com.kh.reactrip.member.model.dto.UpdateNameRequest;
import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.common.ResponseData;
import com.kh.reactrip.member.model.service.MemberService;

import jakarta.validation.Valid;
import com.kh.reactrip.member.model.dto.SignupRequest;
import com.kh.reactrip.common.ResponseData;
import com.kh.reactrip.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/members")
@Qualifier("MemberServiceImpl")
public class MemberController {
	
	private final MemberService memberService;
	
	public MemberController(@Qualifier("memberServiceImpl") MemberService memberService) {  
		this.memberService = memberService;
	}

	
	@PostMapping("/signup")
	public ResponseEntity<ResponseData<Object>>signUp(@RequestBody SignupRequest request) {
		
		memberService.signUp(request);
		return ResponseData.ok("회원가입 성공");
		
	}

	@GetMapping("/mypage")
	public ResponseEntity<ResponseData<MemberLoginDTO>> getInfo(@AuthenticationPrincipal UserDetails userDetails) {
		String memberId = userDetails.getUsername();
		MemberLoginDTO member = memberService.getMemberInfo(memberId);
		return ResponseData.ok(member);
	}
	
	@PutMapping("/mypage/name")
	public ResponseEntity<ResponseData<Void>> updateName(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateNameRequest request) {
		String memberId = userDetails.getUsername();
		memberService.updateMemberName(memberId, request.getMemberName());
		return ResponseData.ok(null, "이름이 변경되었습니다.");
	}
	
//	@PutMapping("/profile/image/{memberId}")
//	public ResponseEntity<ResponseData<Object>> updateProfileImage(@PathVariable Long memberId,
//																   @RequestParam MultipartFile profileImage) {
//		
//		memberService.updateProfileImage(memberId, profileImage);
//		return ResponseData.ok("프로필 이미지 변경 성공");
//	}
//	public ResponseEntity<ResponseData<Object>> signUp(@RequestBody SignupRequest request) {
//		
//		memberService.signUp(request);
//
//		return ResponseData.ok("회원가입 성공");
//		
//	}
	

}
