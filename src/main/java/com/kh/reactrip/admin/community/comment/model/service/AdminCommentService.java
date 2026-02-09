package com.kh.reactrip.admin.community.comment.model.service;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
import com.kh.reactrip.common.PageResponseDTO;

public interface AdminCommentService {

	PageResponseDTO<AdminCommentDTO> findAllComment(int page);

	AdminCommentDTO findByCommentNo(Long commentNo);

	AdminCommentDTO deleteStatus(Long commentNo, AdminCommentDTO dto);

	PageResponseDTO<AdminCommentDTO> findBySearch(String keyword, int page);

}
