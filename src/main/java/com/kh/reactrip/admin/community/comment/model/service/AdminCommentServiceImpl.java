package com.kh.reactrip.admin.community.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDetailDTO;
import com.kh.reactrip.admin.community.comment.model.mapper.AdminCommentMapper;
import com.kh.reactrip.admin.community.comment.model.vo.AdminCommentVO;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;
import com.kh.reactrip.util.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {

	private final AdminCommentMapper adminCommentMapper;

	private final Pagenation pagenation;

	@Override
	public PageResponseDTO<AdminCommentDTO> findAllComment(int page) {

		int totalCount = adminCommentMapper.getTotalCount();

		PageInfo pi = pagenation.getPageInfo(totalCount, page);

		Validator.validatePage(page, pi.getMaxPage());
		
		List<AdminCommentVO> voList = adminCommentMapper.findAllComment(pagenation.createRowBounds(pi));
		
		List<AdminCommentDTO> dtoList = voList.stream()
				.map((AdminCommentVO vo) -> {
					return new AdminCommentDTO(vo);
				})
				.toList();

		return new PageResponseDTO<>(pi, dtoList);
	}

	@Override
	public AdminCommentDetailDTO findByCommentNo(Long commentNo) {
		return null;
	}

}
