package com.kh.reactrip.diary.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.reactrip.diary.model.dto.DiaryCommentDTO;
import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.dto.DiaryImageDTO;
import com.kh.reactrip.diary.model.vo.DiaryComListVO;
  
@Mapper  
public interface DiaryMapper {

	List<DiaryDTO> findAllDiary(@Param("offset") int offset, @Param("size") int size);

	int findDiaryCount();

	DiaryDetailDTO findByDiaryNo(int diaryNo);

	List<DiaryComListVO> findByComments(@Param("diaryNo") int diaryNo, @Param("startRow") int size, @Param("endRow") int offset);

	int countByComments(@Param("diaryNo") int diaryNo);

	void insertDiary(DiaryDTO diary);

	List<String> findDiaryImages(int diaryNo);
	
	void insertDiaryImage(@Param("diaryNo") int diaryNo, @Param("imageUrl") String imageUrl);



	 
	
}
