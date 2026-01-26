package com.kh.reactrip.admin.notices.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.admin.notices.model.dto.AdminNoticeDTO;
import com.kh.reactrip.admin.notices.model.service.AdminNoticeService;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notices")
public class AdminNoticeController {
	
	private final AdminNoticeService adminNoticeService;
	 
	@PostMapping("/insert")
	public ResponseEntity<ResponseData<String>> insertNotice(@ModelAttribute AdminNoticeDTO adminNoticeDTO, 
			@RequestParam(value = "file", required = true)MultipartFile file) {
		
		adminNoticeService.insertNotice(adminNoticeDTO, file);
		
		return ResponseData.created("공지사항 등록 성공");
	}
	
	@GetMapping
	public ResponseEntity<ResponseData<PageResponseDTO<AdminNoticeDTO>>> findAllNotice(
			@RequestParam(name = "page", defaultValue = "1") int page)  {
		
		PageResponseDTO<AdminNoticeDTO> list = adminNoticeService.findAllNotice(page);
		
		return ResponseData.ok(list, "공지사항 목록 조회 성공");
	}

}