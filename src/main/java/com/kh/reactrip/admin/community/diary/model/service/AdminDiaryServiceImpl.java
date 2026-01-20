package com.kh.reactrip.admin.community.diary.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDetailDTO;
import com.kh.reactrip.admin.community.diary.model.mapper.AdminDiaryMapper;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryImageVO;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryVO;
import com.kh.reactrip.admin.members.model.mapper.AdminMemberMapper;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.exception.NoticeNotFoundException;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;
import com.kh.reactrip.util.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminDiaryServiceImpl implements AdminDiaryService {

	private final AdminDiaryMapper adminDiaryMapper;
	private final Pagenation pagenation;

	@Override
	public PageResponseDTO<AdminDiaryDTO> findAllDiary(int page) {

		int totalCount = adminDiaryMapper.getTotalCount();

		PageInfo pi = pagenation.getPageInfo(totalCount, page);
		
		List<AdminDiaryDTO> diary = adminDiaryMapper.findAllDiary(pagenation.createRowBounds(pi));

		return new PageResponseDTO<>(pi, diary);
	}

	@Override
	public AdminDiaryDetailDTO findByDiaryNo(Long diaryNo) {
		
		Validator.validateNo(diaryNo, "조회할 일기 번호가 잘못됐습니다.");
		
		AdminDiaryVO vo = adminDiaryMapper.findByDiaryNo(diaryNo);
		
		Validator.validateExist(vo, "해당 게시글을 찾을 수 없습니다.");
		
		List<AdminDiaryImageVO> imageList = adminDiaryMapper.findImagesByDiaryNo(diaryNo);
		
		AdminDiaryDetailDTO detailDto = new AdminDiaryDetailDTO(vo, imageList);
		
		return detailDto;
	}

	@Override
	public AdminDiaryDTO deleteStatus(Long diaryNo, AdminDiaryDTO dto) {
		
		Validator.validateNo(diaryNo, "변경할 일기 번호가 잘못되었습니당.");
		
		dto.setDiaryNo(diaryNo);
		
		AdminDiaryVO vo = new AdminDiaryVO(dto);
		
		int result = adminDiaryMapper.deleteStatus(vo);
		
		Validator.validateExist(vo, "해당 일기의 상태를 변경할 수 없습니다. ");
		
		return findByDiaryNo(diaryNo);
	}

	@Override
	public PageResponseDTO<AdminDiaryDetailDTO> findByDiarySearch(String keyword, int page) {
		
		if(keyword == null || keyword.trim().isEmpty()) {
			return new PageResponseDTO<>(new PageInfo(), new ArrayList<>());
		}
		
		int totalCount = adminDiaryMapper.getSearchCount(keyword);
		
		if(totalCount == 0) {
			log.error("Diary Not Found Exception : {}", keyword);
			throw new NoticeNotFoundException(keyword);
		}
		
		PageInfo pi = pagenation.getPageInfo(totalCount, page);
		
		List<AdminDiaryDetailDTO> list = adminDiaryMapper.findByDiarySearch(keyword, pagenation.createRowBounds(pi));

		return new PageResponseDTO<>(pi, list);
	}

	
}
