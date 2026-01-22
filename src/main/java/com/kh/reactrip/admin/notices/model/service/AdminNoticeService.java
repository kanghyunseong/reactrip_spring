package com.kh.reactrip.admin.notices.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.admin.notices.model.dto.AdminNoticeDTO;
import com.kh.reactrip.common.PageResponseDTO;

public interface AdminNoticeService {

	void insertNotice(AdminNoticeDTO adminNoticeDTO, MultipartFile file);

	PageResponseDTO<AdminNoticeDTO> findAllNotice(int page);

}
