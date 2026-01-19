package com.kh.reactrip.diary.model.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.kh.reactrip.diary.model.dto.DiaryDTO;

public interface DiaryService {

	Map<String, Object> findAllDiary(int page, int size);

	DiaryDTO findByDiaryNo(int diaryNo);







}
