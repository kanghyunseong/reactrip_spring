package com.kh.reactrip.diary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.diary.model.service.DiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiaryController {

	private final DiaryService diaryService;
	
	
	// 목록 전체 조회
	@GetMapping("")
	public ResponseEntity<?> findAllDiary(@RequestParam(name="pageNo", defaultValue = "1")int pageNo) {

		
		return null;
	}
	
}
