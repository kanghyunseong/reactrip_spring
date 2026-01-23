package com.kh.reactrip.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.member.model.dto.SignupRequest;
import com.kh.reactrip.member.model.dto.UpdateBirthdayRequest;
import com.kh.reactrip.member.model.dto.UpdateEmailRequest;
import com.kh.reactrip.member.model.dto.UpdateNameRequest;
import com.kh.reactrip.member.model.dto.UpdatePasswordRequest;
import com.kh.reactrip.member.model.dto.UpdatePhoneRequest;
import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.common.ResponseData;
import com.kh.reactrip.member.model.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Validated
public class MemberController {
	
	private final MemberService memberService;
	
	@PostMapping("/signup")
	public ResponseEntity<ResponseData<Object>>signUp(@Valid @RequestBody SignupRequest request) {
		
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
	
	@PutMapping("/mypage/email")
	public ResponseEntity<ResponseData<Void>> updateEmail(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UpdateEmailRequest request) {
		String memberId = userDetails.getUsername();
		memberService.updateMemberEmail(memberId, request.getEmail());
		
		return ResponseData.ok(null, "이메일이 변경되었습니다.");
	}
	
	@PutMapping("/mypage/phone")
	public ResponseEntity<ResponseData<Void>> updatePhone(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UpdatePhoneRequest request) {
		String memberId = userDetails.getUsername();
		memberService.updateMemberPhone(memberId, request.getPhone());
		
		return ResponseData.ok(null, "번호가 변경되었습니다.");
	}
	
	@PutMapping("/mypage/birthday")
	public ResponseEntity<ResponseData<Void>> updateBirthday(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UpdateBirthdayRequest request) {
		String memberId = userDetails.getUsername();
		memberService.updateMemberBirthday(memberId, request.getBirthDay());
		
		return ResponseData.ok(null, "생년월일이 변경되었습니다.");
	}
	
	@PutMapping("/mypage/password")
	public ResponseEntity<ResponseData<Void>> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody UpdatePasswordRequest request) {
		String memberId = userDetails.getUsername();
		memberService.updateMemberPassword(memberId, request);
		
		return ResponseData.ok(null, "비밀번호가 변경되었습니다");
	}
	
//	@PutMapping("/mypage/profile")
//	public ResponseEntity<ResponseData<Void>> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody )
//	@PutMapping("/profile/image/{memberId}")
//	public ResponseEntity<ResponseData<Object>> updateProfileImage(@PathVariable Long memberId,
//																   @RequestParam MultipartFile profileImage) {
//		
//		memberService.updateProfileImage(memberId, profileImage);
//		return ResponseData.ok("프로필 이미지 변경 성공");
//	}
}
