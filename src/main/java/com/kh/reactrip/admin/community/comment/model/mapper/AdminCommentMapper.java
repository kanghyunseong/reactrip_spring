package com.kh.reactrip.admin.community.comment.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDetailDTO;
import com.kh.reactrip.admin.community.comment.model.vo.AdminCommentVO;

@Mapper
public interface AdminCommentMapper {

	int getTotalCount();

	List<AdminCommentVO> findAllComment(RowBounds rowBounds);
	
	AdminCommentVO findByCommentNo(Long commentNo);

	int deleteStatus(AdminCommentVO vo);

	int getSearchCount(String keyword);

	List<AdminCommentDetailDTO> findBySearch(String keyword, RowBounds rowBounds);

}
