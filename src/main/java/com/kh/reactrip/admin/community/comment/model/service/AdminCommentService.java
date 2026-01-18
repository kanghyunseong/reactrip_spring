package com.kh.reactrip.admin.community.comment.model.service;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDetailDTO;
import com.kh.reactrip.common.PageResponseDTO;

public interface AdminCommentService {

	PageResponseDTO<AdminCommentDTO> findAllComment(int page);

	AdminCommentDetailDTO findByCommentNo(Long commentNo);

}
