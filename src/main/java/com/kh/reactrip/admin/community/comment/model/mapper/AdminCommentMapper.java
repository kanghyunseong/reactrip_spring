package com.kh.reactrip.admin.community.comment.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;

@Mapper
public interface AdminCommentMapper {

	int getTotalCount();

	List<AdminCommentDTO> findAllComment(RowBounds rowBounds);
	
	AdminCommentDTO findByCommentNo(Long commentNo);

	int deleteStatus(Long commentNo);

	int getSearchCount(String keyword);

	List<AdminCommentDTO> findBySearch(String keyword, RowBounds rowBounds);

}
