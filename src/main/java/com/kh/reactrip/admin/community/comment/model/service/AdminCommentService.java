package com.kh.reactrip.admin.community.comment.model.service;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
import com.kh.reactrip.common.PageResponseDTO;

public interface AdminCommentService {

	PageResponseDTO<AdminCommentDTO> findAllComment(int page);

	AdminCommentDTO findByCommentNo(Long commentNo);

	void deleteComment(Long commentNo);

	PageResponseDTO<AdminCommentDTO> findBySearch(String keyword, int page);

}
