package com.kh.reactrip.place.model.service;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.kh.reactrip.exception.PlaceNotFoundException;
import com.kh.reactrip.place.model.dao.PlaceMapper;
import com.kh.reactrip.place.model.dto.PlaceDTO;
import com.kh.reactrip.util.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class PlaceServiceImpl implements PlaceService {
	
	private final PlaceMapper placeMapper;
	
	// RowBounds용 상수
	private static final Integer PAGE_SIZE = 10;
	
	public List<PlaceDTO> findAllPlace(String keyword, Long themeNo, Long regionNo, Integer page, Integer size, String sort) {
		
		log.info("키워드가 전달되나요? : {}", keyword);
		
		PageInfo pi = new PageInfo();
		pi.setCurrentPage(page);
		pi.setBoardLimit(size);
		
		RowBounds rb = getRowBounds(pi);
		
		Map<String, Object> query = new HashMap();
		query.put("keyword", keyword);
		query.put("themeNo", themeNo);
		query.put("regionNo", regionNo);
		query.put("sort", sort);
		
		log.info("Map에 키워드 제대로 들어갔음? : {}", query.get("keyword"));
		
		List<PlaceDTO> places = placeMapper.findAllPlace(query, rb);
		
		if(places.isEmpty()) {
			throw new PlaceNotFoundException("조회된 여행지가 없습니다.");
		}
		
		return places;
		
	}
	
	// RowBounds
	private RowBounds getRowBounds(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * PAGE_SIZE;
        return new RowBounds(offset, PAGE_SIZE);
    }
	
	public PlaceDTO findByTravelNo(String travelNo) {
		
		Long parsedTravelNo = Long.parseLong(travelNo);
		
		if(parsedTravelNo < 1) {
			throw new InvalidParameterException("유효하지 않은 접근입니다."); // 커스텀 예외 아님, 값이 0 또는 음수일 경우
		}
		
		PlaceDTO place = placeMapper.findByTravelNo(parsedTravelNo);
		
		if(place == null) {
			throw new PlaceNotFoundException("조회된 여행지가 없습니다.");
		}
		
		return place;
		
	}

}
