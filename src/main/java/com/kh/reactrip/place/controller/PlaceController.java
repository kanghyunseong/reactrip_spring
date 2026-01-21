package com.kh.reactrip.place.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.reactrip.place.model.dto.PlaceDTO;
import com.kh.reactrip.place.model.service.PlaceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 확인용
@RestController
@Validated // 여행지 목록 전체 조회 시 전달값 검증
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
	
	private final PlaceService placeService;
		
	@GetMapping
	public ResponseEntity<List<PlaceDTO>> findAllPlace(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "themeNo", required = false) Long themeNo,
			@RequestParam(value = "regionNo", required = false) Long regionNo,
			@RequestParam(value = "page", defaultValue="0", required = false) Integer page, // 페이지 번호, PageInfo의 currentPage
			@RequestParam(value = "size", defaultValue="10", required = false) Integer size, // 페이지 크기, PageInfo의 boardLimit
			@RequestParam(value = "sort", required = false) String sort
			) {
		
		List<PlaceDTO> places = placeService.findAllPlace(keyword, themeNo, regionNo, page, size, sort);
		return ResponseEntity.ok(places);
		
	}
	
	@GetMapping("/{travelNo}")
	public ResponseEntity<PlaceDTO> findByTravelNo(@PathVariable(name="travelNo") Long travelNo) {
		
		PlaceDTO place = placeService.findByTravelNo(travelNo);
		return ResponseEntity.ok(place);
		
	}

}
