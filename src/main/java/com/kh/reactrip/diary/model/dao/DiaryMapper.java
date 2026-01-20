package com.kh.reactrip.diary.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.reactrip.diary.model.dto.DiaryCommentDTO;
import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;

@Mapper
public interface DiaryMapper {

	List<DiaryDTO> findAllDiary(@Param("size") int size, @Param("offset") int offset);

	int findDiaryCount();

	DiaryDetailDTO findByDiaryNo(int diaryNo);

	DiaryCommentDTO findByComments(int diaryNo, int page);


	 
	
}
