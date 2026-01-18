package com.kh.reactrip.admin.community.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDetailDTO;
import com.kh.reactrip.admin.community.comment.model.service.AdminCommentService;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/community/comments")
public class AdminCommentController {
	
	private final AdminCommentService adminCommentService;
	

	// 전체 목록 조회
	@GetMapping
	public ResponseEntity<ResponseData<PageResponseDTO<AdminCommentDTO>>> findAllComment(
			@RequestParam(name = "page", defaultValue = "1")int page){
		
		PageResponseDTO<AdminCommentDTO> list = adminCommentService.findAllComment(page);
		
		return ResponseData.ok("목록 조회 성공", list);
	}
	
	@GetMapping("/{commentNo}")
	public ResponseEntity<ResponseData<AdminCommentDetailDTO>> findByCommentNo(
			@PathVariable(name = "commentNo")Long commentNo) {
		
		AdminCommentDetailDTO detailDTO = adminCommentService.findByCommentNo(commentNo);
		
		return ResponseData.ok("상세조회 성공하였습니다.", detailDTO);
	}
	 
}
