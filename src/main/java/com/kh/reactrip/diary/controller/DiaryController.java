package com.kh.reactrip.diary.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.service.DiaryService;
import com.kh.reactrip.diary.model.vo.DiaryComListVO;
import com.kh.reactrip.diary.model.vo.DiaryComVO;
import com.kh.reactrip.diary.model.vo.DiaryDetailVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diarys")
public class DiaryController {

	private final DiaryService diaryService;

	// 목록 전체 조회
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAllDiary(@RequestParam(name = "page", defaultValue = "1") int page, // 한
																														// 페이지에
			@RequestParam(name = "size", defaultValue = "5") int size) { // 게시글 5개 보여줘

		// Map<String, Object> map = diaryService.findAllDiary(page, size);
		// log.info("{}", diaryService.findAllDiary(1, size));
		log.info("컨트롤러 page --> " + page);

		return ResponseEntity.ok(diaryService.findAllDiary(page, size));
	}

	// 상세 조회
	@GetMapping("/{diaryNo}")
	public ResponseEntity<DiaryDetailDTO> findByDiaryNo(@PathVariable(name="diaryNo") int diaryNo) {

		// log.info("상세조회 : " + ddVO );

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
	public ResponseEntity<?> insertDiary(@RequestPart("data") DiaryDTO dto,
			@RequestPart(value = "images", required = false) List<MultipartFile> images) {

		diaryService.insertDiary(dto, images);
		return ResponseEntity.ok().build();
	}

}
