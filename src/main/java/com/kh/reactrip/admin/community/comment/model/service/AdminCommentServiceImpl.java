package com.kh.reactrip.admin.community.comment.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDetailDTO;
import com.kh.reactrip.admin.community.comment.model.mapper.AdminCommentMapper;
import com.kh.reactrip.admin.community.comment.model.vo.AdminCommentVO;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryVO;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.file.service.FileService;
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
		
		Validator.validateNo(commentNo, "상세조회할 게시글을 찾지 못했습니다.");
		
		AdminCommentVO vo  = adminCommentMapper.findByCommentNo(commentNo);
		
		Validator.validateExist(vo, "조회할 게시글 번호가 잘못됨");
		
		AdminCommentDetailDTO dtoDetail = new AdminCommentDetailDTO(vo);
		
		return dtoDetail;
	}
	
	@Override
	public AdminCommentDetailDTO deleteComment(Long commentNo, AdminCommentDetailDTO dto) {

		
		dto.setCommentNo(commentNo);
		
		Validator.validateNo(commentNo, "삭제할 번호가 잘못됨.");
		
		AdminCommentVO vo = new AdminCommentVO(dto);
		
		int result = adminCommentMapper.deleteStatus(vo);
		
		Validator.validateExist(vo, "대상을 찾을 수 없습니다.");
		
		return findByCommentNo(commentNo);
	}

	@Override
	public PageResponseDTO<AdminCommentDetailDTO> findBySearch(String keyword, int page) {

		if(keyword == null || keyword.trim().isEmpty()) {
			return new PageResponseDTO<>(new PageInfo(), new ArrayList<>());
		}
		
		int totalCount = adminCommentMapper.getSearchCount(keyword);
		
		
		if(totalCount == 0) {
			log.error("Comment Not Found Exception : {} ", keyword);
			throw new RuntimeException(keyword);
		}
		
		PageInfo pi = pagenation.getPageInfo(totalCount, page);
		
		List<AdminCommentDetailDTO> list = adminCommentMapper.findBySearch(keyword, pagenation.createRowBounds(pi));
		
		return new PageResponseDTO<>(pi, list);
	}

}
