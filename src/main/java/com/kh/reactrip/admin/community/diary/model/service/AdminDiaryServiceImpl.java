package com.kh.reactrip.admin.community.diary.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
import com.kh.reactrip.admin.community.diary.model.mapper.AdminDiaryMapper;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryImageVO;
import com.kh.reactrip.common.PageResponseDTO;
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
	public AdminDiaryDTO findByDiaryNo(Long diaryNo) {
		
		Validator.validateNo(diaryNo, "조회할 일기 번호가 잘못됐습니다.");
		
		AdminDiaryDTO dto = adminDiaryMapper.findByDiaryNo(diaryNo);
		Validator.validateExist(dto, "해당 게시글을 찾을 수 없습니다.");

		List<AdminDiaryImageVO> imageList = adminDiaryMapper.findImagesByDiaryNo(diaryNo);
		
		dto.setImages(imageList);
		return dto;
	}

	@Override
    public AdminDiaryDTO deleteStatus(Long diaryNo, AdminDiaryDTO dto) {
		
        Validator.validateNo(diaryNo, "변경할 일기 번호가 잘못되었습니다.");
        
        dto.setDiaryNo(diaryNo);
        int result = adminDiaryMapper.deleteStatus(dto);
        Validator.validateResult(result, "상태 변경 실패");

        return adminDiaryMapper.findByDiaryNo(diaryNo);
    }

	@Override
	public PageResponseDTO<AdminDiaryDTO> findByDiarySearch(String keyword, int page) {

		if (keyword == null || keyword.trim().isEmpty()) {
			return new PageResponseDTO<>(new PageInfo(), new ArrayList<>());
		}

		int totalCount = adminDiaryMapper.getSearchCount(keyword);

		if (totalCount == 0) {
			PageInfo pi = pagenation.getPageInfo(0, page);
			return new PageResponseDTO<>(pi, new ArrayList<>());
		}

		PageInfo pi = pagenation.getPageInfo(totalCount, page);

		List<AdminDiaryDTO> list = adminDiaryMapper.findByDiarySearch(keyword, pagenation.createRowBounds(pi));

		return new PageResponseDTO<>(pi, list);
	}

}
