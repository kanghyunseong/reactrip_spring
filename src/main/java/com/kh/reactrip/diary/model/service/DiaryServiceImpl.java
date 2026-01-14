package com.kh.reactrip.diary.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.reactrip.diary.model.dao.DiaryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

	private final DiaryMapper diaryMapper;
	
	
	
	// 전체 목록 조회
	@Override
	public Map<String, Object> findAllDiary(int pageNo) {
		
		
		return null;
		
	}
}
