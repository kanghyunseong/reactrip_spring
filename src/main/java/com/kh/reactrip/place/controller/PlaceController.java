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
import com.kh.reactrip.place.model.dto.RegionDTO;
import com.kh.reactrip.place.model.dto.ThemeDTO;
import com.kh.reactrip.place.model.service.PlaceService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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
			@RequestParam(value = "page", defaultValue="1", required = false) @Min(value=1, message="유효하지 않은 값입니다.") Integer page, // 페이지 번호, PageInfo의 currentPage -> 0에서 1로 수정, @Min 추가
			@RequestParam(value = "size", defaultValue="10", required = false) Integer size, // 페이지 크기, PageInfo의 boardLimit
			@RequestParam(value = "sort", required = false) String sort
			) {
		
		log.info("키워드가 들어오는지? : {}", keyword);
		
		List<PlaceDTO> places = placeService.findAllPlace(keyword, themeNo, regionNo, page, size, sort);
		return ResponseEntity.ok(places);
		
	}

	/** 구체 경로를 path variable보다 먼저 선언 → /regions, /themes가 /{travelNo}에 잡히지 않음 */
	@GetMapping("/regions")
	public ResponseEntity<List<RegionDTO>> findAllRegion() {
		List<RegionDTO> regions = placeService.findAllRegion();
		return ResponseEntity.ok(regions);
	}

	@GetMapping("/themes")
	public ResponseEntity<List<ThemeDTO>> findAllTheme() {
		List<ThemeDTO> themes = placeService.findAllTheme();
		return ResponseEntity.ok(themes);
	}

	@GetMapping("/{travelNo}")
	public ResponseEntity<PlaceDTO> findByTravelNo(@PathVariable(name="travelNo") @Validated @Pattern(regexp="^[0-9]+$", message="유효하지 않은 접근입니다.") String travelNo) {
		 
		PlaceDTO place = placeService.findByTravelNo(travelNo);
		return ResponseEntity.ok(place);
		
	}
}