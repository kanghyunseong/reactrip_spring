package com.kh.reactrip.admin.community.diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDetailDTO;
import com.kh.reactrip.admin.community.diary.model.service.AdminDiaryService;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/community/diaries")
public class AdminDiaryController {
	
	private final AdminDiaryService adminDiaryService;
	
	@GetMapping
	public ResponseEntity<ResponseData<PageResponseDTO<AdminDiaryDTO>>> findAllDiary(
			@RequestParam(name = "page", defaultValue = "1")int page) {
		
		PageResponseDTO<AdminDiaryDTO> list = adminDiaryService.findAllDiary(page);
		
		return ResponseData.ok("목록 조회 성공", list);
	}
	
	@GetMapping("/{diaryNo}")
	public ResponseEntity<ResponseData<AdminDiaryDetailDTO>> findByDiaryNo(@PathVariable(name = "diaryNo")Long diaryNo) { 
		AdminDiaryDetailDTO detail = adminDiaryService.findByDiaryNo(diaryNo);
	    return ResponseData.ok("상세 조회 성공", detail);
	}
	
	@DeleteMapping("/{diaryNo}")
	public ResponseEntity<ResponseData<AdminDiaryDTO>> updateDiaryStatus(
			@PathVariable(name = "diaryNo")Long diaryNo
		  , @RequestBody AdminDiaryDTO dto) {
		AdminDiaryDTO status = adminDiaryService.updateDiaryStatus(diaryNo, dto);
		return ResponseData.noContent();
	}

}
