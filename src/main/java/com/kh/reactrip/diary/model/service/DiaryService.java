package com.kh.reactrip.diary.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.kh.reactrip.diary.model.dto.DiaryCommentDTO;
import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.vo.DiaryComListVO;

public interface DiaryService {

	Map<String, Object> findAllDiary(int page, int size);

	DiaryDetailDTO findByDiaryNo(int diaryNo);

	List<DiaryComListVO> findByComments(int diaryNo, int page);

}
