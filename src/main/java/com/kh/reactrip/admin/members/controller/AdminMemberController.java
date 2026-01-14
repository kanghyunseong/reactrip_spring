package com.kh.reactrip.admin.members.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	/*
	// 2. 회원 삭제 기능 구현 
	@DeleteMapping("{memberNo}")
	public ResponseEntity<ResponseData<Void>> deleteMembers(@PathVariable(name = "memberNo")Long memberNo, @AuthenticationPrincipal CustomUserDetails adminUserDetails ) {
		 
		
		
		return null;
	}
	*/
	// 회원 검색어로 조회 
	public ResponseEntity<ResponseData<AdminMemberDTO>> searchMembers(@PathVariable(name="memberNo") Long memberNo) {
		
		AdminMemberDTO searchMember = memberService.searchMember(memberNo);
		
		return ResponseData.ok(searchMember, "검색어로 조회 성공 ");
	}
	
	// 회원 정보 변경 -> 권한 부여하기 
	
}
