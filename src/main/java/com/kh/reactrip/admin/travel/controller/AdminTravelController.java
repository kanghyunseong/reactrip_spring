package com.kh.reactrip.admin.travel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.admin.travel.model.dto.AdminTravelDTO;
import com.kh.reactrip.admin.travel.model.service.AdminTravelService;
import com.kh.reactrip.common.PageResponseDTO;
import com.kh.reactrip.common.ResponseData;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/travel")
@RequiredArgsConstructor
public class AdminTravelController {
	
	private final AdminTravelService adminTravelService;
	
	@GetMapping
	public ResponseEntity<ResponseData<PageResponseDTO<AdminTravelDTO>>> findAllTravel(@RequestParam(name = "page", defaultValue = "1")int page) {

		PageResponseDTO<AdminTravelDTO> response = adminTravelService.findAllTravel(page);
		
		return ResponseData.ok("여행지 목록 조회 성공. ", response);
		
	}
	
	@GetMapping("/regions")
	public ResponseEntity<ResponseData<List<Map<String, Object>>>> findAllRegions() {
		List<Map<String, Object>> regions = adminTravelService.findAllRegions();
		return ResponseData.ok("지역 목록 조회 성공", regions);
	}
	
	@DeleteMapping("/{travelNo}")
    public ResponseEntity<ResponseData<AdminTravelDTO>> updateTravelStatus(
            @PathVariable(name = "travelNo") Long travelNo,
            @RequestParam(name = "status") String status) {
        
        AdminTravelDTO response = adminTravelService.updateTravelStatus(travelNo, status);
        
        return ResponseData.ok("여행지 상태 변경 성공", response);
    }
	@PostMapping("/insert")
	public ResponseEntity<ResponseData<String>> insertTravel(
			@ModelAttribute AdminTravelDTO adminTravelDTO,
			@RequestParam(value = "file", required = false)MultipartFile file
			) {
		adminTravelService.insertTravel(adminTravelDTO, file);
		
		return ResponseData.created("여행지 등록 성공");
	}
	
	@PutMapping("/{travelNo}")
	public ResponseEntity<ResponseData<AdminTravelDTO>> updateTravel(
			@PathVariable(name = "travelNo")Long travelNo,
			@RequestParam(value = "file", required = false)MultipartFile file,
			@ModelAttribute AdminTravelDTO adminTravelDTO) {
		
		adminTravelService.updateTravel(travelNo, file, adminTravelDTO);
		
		return ResponseData.ok("여행지 수정 완료.", null);
	}
	
	@PostMapping("/api-sync")
	public ResponseEntity<ResponseData<String>> syncApiData() {
		adminTravelService.fetchAndSaveApiData();
		return ResponseData.ok("데이터 동기화 완료");
	}
	
	@GetMapping("/nearby")
	public ResponseEntity<ResponseData<List<AdminTravelDTO>>> getNearby(
			@RequestParam("mapX") double mapX, 
	        @RequestParam("mapY") double mapY
			) {
		
		List<AdminTravelDTO> list = adminTravelService.getOrSyncNearbyTravels(mapX, mapY);
		
		return ResponseData.ok("", list);
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseData<PageResponseDTO<AdminTravelDTO>>> findBySearch(
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "page") int page
			) {
		
		PageResponseDTO<AdminTravelDTO> list = adminTravelService.findBySearch(keyword, page);
		
		return ResponseData.ok("검색어로 조회 성공",list);
	}
}
