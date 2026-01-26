package com.kh.reactrip.diary.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.service.DiaryService;
import com.kh.reactrip.file.service.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diarys")
public class DiaryController {

	private final DiaryService diaryService;
	private final S3Service s3Service;

	// 목록 전체 조회
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAllDiary(
			@RequestParam(name = "page", defaultValue = "1") int page, 
			@RequestParam(name = "size", defaultValue = "5") int size) { // 게시글 5개 보여줘
		
		// log.info("컨트롤러 page --> " + page);

		return ResponseEntity.ok(diaryService.findAllDiary(page, size));
	}

	// 상세 조회
	@GetMapping("/{diaryNo}")
	public ResponseEntity<DiaryDetailDTO> findByDiaryNo(@PathVariable(name="diaryNo") int diaryNo) {

		DiaryDetailDTO diary = diaryService.findByDiaryNo(diaryNo);

		// log.info("diaryNo = {}", diary.getDiaryNo());

		return ResponseEntity.ok(diary);
	}

	// 댓글 목록 조회
	@GetMapping("/{diaryNo}/comments")
	public ResponseEntity<Map<String, Object>> getComments(@PathVariable("diaryNo") int diaryNo,
			@RequestParam(name="page", defaultValue = "1") int page,
			@RequestParam(name="size", defaultValue = "5") int size) {

		log.info("댓글 가져오기 : " + diaryNo);
		
		Map<String, Object> result = diaryService.findByComments(diaryNo, page, size);
		
		return ResponseEntity.ok(diaryService.findByComments(diaryNo, page, size));
	}

 
	  
	// 게시글 작성
	@PostMapping
	public ResponseEntity<?> insertDiary(@ModelAttribute DiaryDTO diary) {
	    
		diaryService.insertDiary(diary);
	    
		return ResponseEntity.ok().build();
	}
	
	
	
	// S3 테스트용
//	@PostMapping("/test/s3")
//	public String testUpload(@RequestParam("file") MultipartFile file) {
//	    return s3Service.fileSave(file);
//	}

}
