package com.kh.reactrip.admin.community.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
		
		return ResponseData.ok(list, "목록 조회 성공");
	}
	
	@GetMapping("/{commentNo}")
	public ResponseEntity<ResponseData<AdminCommentDetailDTO>> findByCommentNo(
			@PathVariable(name = "commentNo")Long commentNo) {
		
		AdminCommentDetailDTO detailDTO = adminCommentService.findByCommentNo(commentNo);
		
		return ResponseData.ok(detailDTO, "상세조회 성공하였습니다.");
	}
	
	@DeleteMapping("/{commentNo}")
	public ResponseEntity<ResponseData<AdminCommentDetailDTO>> deleteComment(
			@PathVariable(name = "commentNo") Long commentNo,
			@RequestBody AdminCommentDetailDTO dto) {
		
		AdminCommentDetailDTO detailDTO = adminCommentService.deleteComment(commentNo, dto);
		
		return ResponseData.noContent();
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseData<PageResponseDTO<AdminCommentDetailDTO>>> findBySearch(
			@RequestParam(name = "keyword")String keyword,
			@RequestParam(name = "page") int page
			) {
		
		PageResponseDTO<AdminCommentDetailDTO> dtoList = adminCommentService.findBySearch(keyword, page);
		
		return ResponseData.ok(dtoList, "검색 조회 성공 하였습니다.");
	}
	
	 
}
