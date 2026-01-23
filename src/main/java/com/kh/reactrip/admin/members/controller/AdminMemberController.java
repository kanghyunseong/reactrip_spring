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
import com.kh.reactrip.admin.members.model.service.AdminMemberService;
import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    private final AdminMemberService memberService;
    
    // 1. 회원목록 전체 조회 (페이징 적용)
    @GetMapping
    public ResponseEntity<ResponseData<PageResponseDTO<AdminMemberDTO>>> findAllMembers(
            @RequestParam(name = "page", defaultValue = "1") int page) {
        
        // ★ 제네릭 타입 명시
        PageResponseDTO<AdminMemberDTO> list = memberService.findAllMember(page);
        
        // ★ ResponseData.ok(메시지, 데이터) 순서 맞춤
        return ResponseData.ok(list, "회원 목록 조회 성공");
    }
    
    // 2. 회원 삭제
    @DeleteMapping("/{memberNo}")
    public ResponseEntity<ResponseData<Void>> deleteMember(
            @PathVariable(name = "memberNo") Long memberNo, 
            @AuthenticationPrincipal CustomUserDetails adminUserDetails) {
        
        memberService.deleteMember(memberNo);
        
        return ResponseData.noContent();
    }
    
    // 3. 회원 검색 (검색결과도 페이징이 필요하다면 PageResponseDTO를 써야 하지만, 일단 List로 유지)
    @GetMapping("/search")
    public ResponseEntity<ResponseData<PageResponseDTO<AdminMemberDTO>>> findByMembers(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page")int page) {
        
    	PageResponseDTO<AdminMemberDTO> list = memberService.findByMembers(keyword, page);
        
        // ★ ResponseData.ok(메시지, 데이터) 순서 맞춤
        return ResponseData.ok(list, "검색어로 조회 성공");
    }
    
    // 4. 권한 변경
    @PutMapping("/update-role/{memberNo}")
    public ResponseEntity<ResponseData<Void>> updateMemberRole(
            @PathVariable(name = "memberNo") Long memberNo, 
            @RequestParam(name = "memberRole") String memberRole) {
        
        memberService.updateMemberRole(memberNo, memberRole);
        
        // ★ 데이터가 없을 땐 null을 인자로 전달
        return ResponseData.ok(null, "유저 권한 변경 성공");
    }
}