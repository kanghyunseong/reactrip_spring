package com.kh.reactrip.diary.model.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.diary.model.dao.DiaryMapper;
import com.kh.reactrip.diary.model.dto.DiaryCommentDTO;
import com.kh.reactrip.diary.model.dto.DiaryDTO;
import com.kh.reactrip.diary.model.dto.DiaryDetailDTO;
import com.kh.reactrip.diary.model.dto.DiaryImageDTO;
import com.kh.reactrip.diary.model.vo.DiaryComListVO;
import com.kh.reactrip.file.service.S3Service;
import com.kh.reactrip.util.PageInfo;
import com.kh.reactrip.util.Pagenation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@Primary
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private final DiaryMapper diaryMapper;
	private final Pagenation pagenation;
	private final S3Service s3Service;
	
	
	// 전체 목록 조회
	@Override
	public Map<String, Object> findAllDiary(int page, int size) {
		
		// 페이지 처리 유효성 검사
		if(page < 1) {
			throw new InvalidParameterException("잘못된 페이지 요청입니다.");
		} 
		
		int offset = (page - 1) * size;

		List<DiaryDTO> diaryList = diaryMapper.findAllDiary(offset, size);
		
		int totalCount = diaryMapper.findDiaryCount();

		log.info("전제페이지 :  " + totalCount);
		
		log.info("page={}, size={}, offset={}", page, size, offset);
		
		Map<String, Object> map = new HashMap();
		
		map.put("diary", diaryList);
		map.put("totalCnt", totalCount); 
		map.put("limitPage", size);
		map.put("currentPage", page);
		
		return map;
	}
 

	// 상세 조회
	@Override  
	public DiaryDetailDTO findByDiaryNo(int diaryNo) {
		
		if(diaryNo <= 0) {
			throw new InvalidParameterException("잘못된 다이어리 번호입니다.");
		}
		
		DiaryDetailDTO ddDTO = diaryMapper.findByDiaryNo(diaryNo);
		
		List<String> imageUrls = diaryMapper.findDiaryImages(diaryNo);
		
		log.info("상세조회 : {}", ddDTO);

		ddDTO.setImageUrls(imageUrls);
		
		return ddDTO;
	}


	// 댓글 목록 조회
	@Override
	public Map<String, Object> findByComments(int diaryNo, int page, int size) {
		
		log.info("게시글 번호 : " + diaryNo);
		
		int startRow = (page - 1) * size + 1;
		
		int endRow = page * size;
		
		List<DiaryComListVO> listVo = diaryMapper.findByComments(diaryNo, startRow, endRow);
		
		int totalCount = diaryMapper.countByComments(diaryNo);
		
		int totalPage = (int) Math.ceil((double) totalCount / size);
		
		Map<String, Object> result = new HashMap<>();
		    result.put("listVo", listVo);
		    result.put("page", page);
		    result.put("size", size);
		    result.put("totalCount", totalCount);
		    result.put("totalPage", totalPage);

		    return result;
		}
	
	// 게시글 작성
	@Transactional
	@Override
	public void insertDiary(DiaryDTO diary) {
		
		diaryMapper.insertDiary(diary);
		
		// 이미지 파일
        if (diary.getImages() == null); 
        
            for (MultipartFile file : diary.getImages()) {

                if (file.isEmpty()) continue;

                String imagePath = s3Service.fileSave(file);

                diaryMapper.insertDiaryImage(diary.getDiaryNo(), imagePath);
            }
		
        }
 






}
