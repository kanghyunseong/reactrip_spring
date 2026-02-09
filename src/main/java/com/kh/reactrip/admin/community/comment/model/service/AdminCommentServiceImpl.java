package com.kh.reactrip.admin.community.comment.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
import com.kh.reactrip.admin.community.comment.model.mapper.AdminCommentMapper;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;
import com.kh.reactrip.util.Validator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Primary
public class AdminCommentServiceImpl implements AdminCommentService {

	private final AdminCommentMapper adminCommentMapper;

	private final Pagenation pagenation;

	@Override
	public PageResponseDTO<AdminCommentDTO> findAllComment(int page) {

		int totalCount = adminCommentMapper.getTotalCount();

		PageInfo pi = pagenation.getPageInfo(totalCount, page);

		Validator.validatePage(page, pi.getMaxPage());
		
		List<AdminCommentDTO> dtoList = adminCommentMapper.findAllComment(pagenation.createRowBounds(pi));

		return new PageResponseDTO<>(pi, dtoList);
	}

	@Override
	public AdminCommentDTO findByCommentNo(Long commentNo) {
		
		Validator.validateNo(commentNo, "상세조회할 게시글을 찾지 못했습니다.");
		
		AdminCommentDTO dto  = adminCommentMapper.findByCommentNo(commentNo);
		Validator.validateExist(dto, "조회할 게시글 번호가 잘못됨");
		return dto;
	}
	
	@Override
	public AdminCommentDTO deleteStatus(Long commentNo, AdminCommentDTO dto) {
		Validator.validateNo(commentNo, "변경할 댓글 번호가 잘못되었습니다.");
		
		dto.setCommentNo(commentNo);
		int result = adminCommentMapper.deleteStatus(dto);
		Validator.validateResult(result, "상태 변경 실패");

		return adminCommentMapper.findByCommentNo(commentNo);
	}

	@Override
	public PageResponseDTO<AdminCommentDTO> findBySearch(String keyword, int page) {

		if(keyword == null || keyword.trim().isEmpty()) {
			return new PageResponseDTO<>(new PageInfo(), new ArrayList<>());
		}
		
		int totalCount = adminCommentMapper.getSearchCount(keyword);
		
		if(totalCount == 0) {
			PageInfo pi = pagenation.getPageInfo(0, page);
			return new PageResponseDTO<>(pi, new ArrayList<>());
		}
		
		PageInfo pi = pagenation.getPageInfo(totalCount, page);
		Validator.validatePage(page, pi.getMaxPage());
		
		List<AdminCommentDTO> list = adminCommentMapper.findBySearch(keyword, pagenation.createRowBounds(pi));
		
		return new PageResponseDTO<>(pi, list);
	}

}
