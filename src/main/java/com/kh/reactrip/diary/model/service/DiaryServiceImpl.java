package com.kh.reactrip.diary.model.service;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.reactrip.diary.model.dao.DiaryMapper;
import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.util.PageInfo;

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
	public Map<String, Object> findAllDiary(int page, int size) {
		
		// 페이지 처리 유효성 검사
		if(page < 1) {
			throw new InvalidParameterException("잘못된 페이지 요청입니다.");
		}
		
		int offset = (page - 1) * size;
		log.info("offset --> "+ offset );
		List<DiaryDTO> diaryList = diaryMapper.findAllDiary(size, offset);
		
		log.info("결과 1번째거 :  " + diaryList.get(0).toString());
		
		int totalCount = diaryMapper.findDiaryCount();

		log.info("전제페이지 :  " + totalCount);
		
		Map<String, Object> map = new HashMap();
		
		map.put("diary", diaryList);
		map.put("totalCnt", totalCount);
		map.put("limitPage", size);
		map.put("currentPage", page);
		
		return map;
	}
}
