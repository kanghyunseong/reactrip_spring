package com.kh.reactrip.diary.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.dto.DiaryImageDTO;
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
	@PostMapping("/insert")
	public ResponseEntity<?> insertDiary(@RequestBody DiaryDTO diary) {
		log.info("게시글 작성 컨트롤 호출!~!");
		log.info("diary = {}", diary);    
		
		diaryService.insertDiary(diary);
	    
		return ResponseEntity.ok().build();
	}
	
	
	// 이미지 업로드
//	@PostMapping(value = "/api/diarys/upload/diary-image",
//			  consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//			//consumes = MediaType.APPLICATION_JSON_VALUE
//	)
//	public ResponseEntity<List<String>> uploadDiaryImages(@RequestParam("file") List<MultipartFile> images) {
//		log.info("이미지 업로드 컨트롤러 호출 !!");
//	    List<String> imageUrls = s3Service.upload(images);
//	    log.info("결과  : " + imageUrls);
//	    return ResponseEntity.ok(imageUrls);
//	}
	
//	@PostMapping("/upload/diary-image")
//	public ResponseEntity<List<String>> insertDiaryImages(@RequestParam("images") List<MultipartFile> images) {
//	
//		// diaryService.insertDiaryImages(imgDTO);
//		
//		return ResponseEntity.ok(s3Service.upload(images);
//	}
	
	
//  S3 테스트용
//	@PostMapping("/test/s3")
//	public String testUpload(@RequestParam("file") MultipartFile file) {
//	    return s3Service.fileSave(file);
//	}

}
