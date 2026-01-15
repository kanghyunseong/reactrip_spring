package com.kh.reactrip.admin.notices.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.admin.notices.model.dto.AdminNoticeDTO;

@Mapper
public interface AdminNoticeMapper {

	int insertNotice(AdminNoticeDTO adminNoticeDTO);

	int getTotalCount();

	List<AdminNoticeDTO> findAllNotice(RowBounds rowBounds);
	
	AdminNoticeDTO selectNoticeDetail(Long noticeNo);

	int updateNotice(AdminNoticeDTO adminNoticeDTO);

}
