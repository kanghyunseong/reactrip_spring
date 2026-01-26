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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.member.model.dto.SignupRequest;
import com.kh.reactrip.member.model.dto.UpdatePasswordRequest;
import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.common.ResponseData;
import com.kh.reactrip.member.model.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    public ResponseEntity<ResponseData<Void>> updateName(@AuthenticationPrincipal UserDetails userDetails, 
    	@RequestParam("memberName")
    	@Pattern(regexp = "^[a-z가-힣]*$", message = "이름은 영어, 한글만 사용 가능합니다.")
		@Size(min = 2, max = 40, message = "이름은 2글자 이상 40글자 이하만 사용할 수 있습니다.")
		@NotBlank(message = "이름은 필수 입력사항입니다." ) String memberName) {
		String memberId = userDetails.getUsername();
	    memberService.updateMemberName(memberId, memberName);
	    
        return ResponseData.ok(null, "이름이 변경되었습니다.");
    }
	
	@PutMapping("/mypage/email")
	public ResponseEntity<ResponseData<Void>> updateEmail(@AuthenticationPrincipal CustomUserDetails userDetails, 
		@RequestParam("email")
		@NotBlank
		@Email(message = "이메일 형식이 올바르지 않습니다") String email) {
		String memberId = userDetails.getUsername();
		memberService.updateMemberEmail(memberId, email);
		
		return ResponseData.ok(null, "이메일이 변경되었습니다.");
	}	
	
	@PutMapping("/mypage/phone")
	public ResponseEntity<ResponseData<Void>> updatePhone(@AuthenticationPrincipal CustomUserDetails userDetails, 
		@RequestParam("phone")
		@Pattern(regexp = "^0\\d{1,2}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
		@NotBlank(message = "전화번호는 필수 입력사항입니다.") String phone) {
		String memberId = userDetails.getUsername();
		memberService.updateMemberPhone(memberId, phone);
		
		return ResponseData.ok(null, "번호가 변경되었습니다.");
	}
	@PutMapping("/mypage/birthday")
	public ResponseEntity<ResponseData<Void>> updateBirthday(@AuthenticationPrincipal CustomUserDetails userDetails, 
		@RequestParam("birthday")
		@Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "생년월일 형식이 올바르지 않습니다.")
		@NotBlank(message = "생년월일는 필수 입력사항입니다.") String birthday) {
		String memberId = userDetails.getUsername();
		memberService.updateMemberBirthday(memberId, birthday);
		
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
