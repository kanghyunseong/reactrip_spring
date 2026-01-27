package com.kh.reactrip.admin.community.diary.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryImageVO;

@Mapper
public interface AdminDiaryMapper {

	int getTotalCount();

	List<AdminDiaryDTO> findAllDiary(RowBounds rowBounds);

	AdminDiaryDTO findByDiaryNo(Long diaryNo);
	
	int deleteStatus(AdminDiaryDTO dto);

	List<AdminDiaryImageVO> findImagesByDiaryNo(Long diaryNo);

	int getSearchCount(String keyword);

	List<AdminDiaryDTO> findByDiarySearch(String keyword, RowBounds rowBounds);

}
