package com.kh.reactrip.diary.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.reactrip.diary.model.dto.DiaryDTO;

@Mapper
public interface DiaryMapper {

	List<DiaryDTO> findAllDiary(@Param("size") int size, @Param("offset") int offset);

	int findDiaryCount();

	DiaryDTO findByDiaryNo(int diaryNo);

	
	
	
}
