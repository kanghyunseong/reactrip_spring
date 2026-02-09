package com.kh.reactrip.diary.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.diary.model.dto.DiaryCommentDTO;
import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.dto.DiaryImageDTO;
import com.kh.reactrip.diary.model.vo.DiaryComListVO;

public interface DiaryService {

	Map<String, Object> findAllDiary(int page, int size);

	DiaryDetailDTO findByDiaryNo(int diaryNo);
  
	Map<String, Object> findByComments(int diaryNo, int page, int size);

	int insertDiary(DiaryDTO diary);

	//void insertDiaryImages(DiaryImageDTO imgDTO);


 
}
