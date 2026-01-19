package com.kh.reactrip.diary.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.auth.model.vo.CustomUserDetails;
import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.service.DiaryService;
import com.kh.reactrip.diary.model.vo.DiaryDetailVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class DiaryController {

	private final DiaryService diaryService;
	
	public DiaryController(@Qualifier("diaryServiceImpl") DiaryService diaryService) {
		    this.diaryService = diaryService;
	}
	 
	// 목록 전체 조회
	@GetMapping("/diarys")
	public ResponseEntity<Map<String, Object>> findAllDiary(
			@RequestParam(name="page", defaultValue = "1") int page,	// 한 페이지에
			@RequestParam(name="size", defaultValue = "5") int size) {		// 게시글 5개 보여줘

		// Map<String, Object> map = diaryService.findAllDiary(page, size);
		//log.info("{}", diaryService.findAllDiary(1, size));
		log.info("컨트롤러 page --> " + page);
		
		return ResponseEntity.ok(diaryService.findAllDiary(page, size));
	}
	  
	 
	// 상세 조회
    @PostMapping("/diarys/detail")
    public ResponseEntity<DiaryDetailVO> findByDiaryNo(@RequestBody DiaryDetailVO in) {
    	log.info("상세조회 : " + in);
    	DiaryDTO diary = diaryService.findByDiaryNo(in.getDiaryNo());
    	
    	in.setDiaryTitle(diary.getDiaryTitle()); //제목
    	in.setDiaryContent(diary.getDiaryContent());  //내용
    	in.setMemberNo(diary.getMemberNo()); //작성자번호
    	in.setMemberName(diary.getMemberName());  // 글작성자명 
    	
    	
        return ResponseEntity.ok(in); 
    }
	
}
