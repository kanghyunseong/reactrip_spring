package com.kh.reactrip.admin.travel.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.reactrip.admin.travel.model.dto.AdminTravelDTO;
import com.kh.reactrip.common.PageResponseDTO;

public interface AdminTravelService {

	PageResponseDTO<AdminTravelDTO> findAllTravel(int page);
	
	AdminTravelDTO updateTravelStatus(Long travelNo, String status);

	void insertTravel(AdminTravelDTO adminTravelDTO, MultipartFile file);

	void updateTravel(Long travelNo, MultipartFile file, AdminTravelDTO adminTravelDTO);

	void fetchAndSaveApiData();

	List<AdminTravelDTO> getOrSyncNearbyTravels(double mapX, double mapY);

	PageResponseDTO<AdminTravelDTO> findBySearch(String keyword, int page);
	
	List<Map<String, Object>> findAllRegions();
	

}
