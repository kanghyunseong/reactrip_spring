package com.kh.reactrip.diary.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.reactrip.diary.model.dao.DiaryMapper;
import com.kh.reactrip.diary.model.dto.DiaryDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private final DiaryMapper diaryMapper;
	
	
	
	// 전체 목록 조회
	@Override
	public Map<String, Object> findAllDiary(int pageNo, int size) {
		
		int offset = (pageNo - 1) * size;
		
		List<DiaryDTO> diaryList = diaryMapper.findAllDiary(size, offset);
		
		int totalCount = diaryMapper.findDiaryCount();
		
		Map<String, Object> map = new HashMap<>();
		
		return map;
	}
}
