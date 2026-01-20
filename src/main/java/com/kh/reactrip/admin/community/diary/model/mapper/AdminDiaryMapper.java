package com.kh.reactrip.admin.community.diary.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDetailDTO;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryImageVO;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryVO;

@Mapper
public interface AdminDiaryMapper {

	int getTotalCount();

	List<AdminDiaryDTO> findAllDiary(RowBounds rowBounds);

	AdminDiaryVO findByDiaryNo(Long diaryNo);
	
	int deleteStatus(AdminDiaryVO vo);

	List<AdminDiaryImageVO> findImagesByDiaryNo(Long diaryNo);

	int getSearchCount(String keyword);

	List<AdminDiaryDetailDTO> findByDiarySearch(String keyword, RowBounds rowBounds);

}
