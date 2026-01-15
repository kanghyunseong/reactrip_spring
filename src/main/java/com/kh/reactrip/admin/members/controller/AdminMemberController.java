package com.kh.reactrip.admin.members.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
import com.kh.reactrip.admin.members.model.dto.AdminPageResponseDTO;
import com.kh.reactrip.admin.members.model.service.AdminMemberService;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {

	private final AdminMemberService memberService;
	
	// 1. 페이징 처리를 통한 회원목록 전체 조회
	@GetMapping
	public ResponseEntity<ResponseData<AdminPageResponseDTO>> findAllMembers(@RequestParam(name = "page", defaultValue = "1") int page) {
		AdminPageResponseDTO list = memberService.findAllMember(page);
		return ResponseData.ok(list, "회원 목록 조회 성공");
	}
	
	
	// 2. 회원 삭제 기능 구현 
	@DeleteMapping("/{memberNo}")
	public ResponseEntity<ResponseData<Void>> deleteMember(@PathVariable(name = "memberNo")Long memberNo, @AuthenticationPrincipal CustomUserDetails adminUserDetails ) {
		 
		memberService.deleteMember(memberNo);
		
		return ResponseData.noContent();
	}
	
	// 회원 검색어로 조회 
	// 회원 검색어로 조회 (URL: /api/admin/members/search?keyword=현성)
	@GetMapping("/search")
	public ResponseEntity<ResponseData<List<AdminMemberDTO>>> findByMembers(
	        @RequestParam(name = "keyword", required = false) String keyword) {
	    
	    // 1. 검색 결과는 여러 명일 수 있으므로 List로 선언
	    List<AdminMemberDTO> list = memberService.findByMembers(keyword);
	    
	    return ResponseData.ok(list, "검색어로 조회 성공");
	}
	
	// 회원 정보 변경 -> 권한 부여하기 
	@PutMapping("/update-role/{memberNo}")
	public ResponseEntity<ResponseData<Void>> updateMemberRole(@PathVariable(name = "memberNo") Long memberNo, @RequestParam(name = "memberRole") String memberRole) {
		
		 memberService.updateMemberRole(memberNo, memberRole);
		
		return ResponseData.ok(null, "검색된 유저의 권한 부여 성공");
		
	}
	
}

/*
 
 package com.kh.reactrip.admin.members.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.admin.members.application.AdminMemberCommandService;
import com.kh.reactrip.admin.members.application.AdminMemberQueryService;
import com.kh.reactrip.admin.members.model.dto.AdminMemberDTO;
import com.kh.reactrip.admin.members.model.dto.AdminPageResponseDTO;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {

	private final AdminMemberQueryService memberQueryService;
	private final AdminMemberCommandService memberCommandService;
	
	// 1. 페이징 처리를 통한 회원목록 전체 조회
	@GetMapping
	public ResponseEntity<ResponseData<AdminPageResponseDTO>> findAllMembers(@RequestParam(name = "page", defaultValue = "1") int page) {
		AdminPageResponseDTO list = memberQueryService.getMembers(page);
		return ResponseData.ok(list, "회원 목록 조회 성공");
	}
	
	
	// 2. 회원 삭제 기능 구현 
	@DeleteMapping("/{memberNo}")
	public ResponseEntity<ResponseData<Void>> deleteMember(@PathVariable(name = "memberNo")Long memberNo, @AuthenticationPrincipal CustomUserDetails adminUserDetails ) {
		 
		memberCommandService.deleteMember(memberNo);
		
		return ResponseData.noContent();
	}
	
	// 회원 검색어로 조회 
	// 회원 검색어로 조회 (URL: /api/admin/members/search?keyword=현성)
	@GetMapping("/search")
	public ResponseEntity<ResponseData<List<AdminMemberDTO>>> findByMembers(
	        @RequestParam(name = "keyword", required = false) String keyword) {
	    
	    // 1. 검색 결과는 여러 명일 수 있으므로 List로 선언
		List<AdminMemberDTO> list = memberQueryService.searchMembers(keyword);
	    
	    return ResponseData.ok(list, "검색어로 조회 성공");
	}
	
	// 회원 정보 변경 -> 권한 부여하기 
	@PutMapping("/update-role/{memberNo}")
	public ResponseEntity<ResponseData<Void>> updateMemberRole(@PathVariable(name = "memberNo") Long memberNo, @RequestParam(name = "memberRole") String memberRole) {
		
		 memberCommandService.updateMemberRole(memberNo, memberRole);
		
		return ResponseData.ok(null, "검색된 유저의 권한 부여 성공");
		
	}
	
}

 
 
 */
