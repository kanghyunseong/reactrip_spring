package com.kh.reactrip.place.model.service;

import java.util.List;

import com.kh.reactrip.place.model.dto.PlaceDTO;
import com.kh.reactrip.place.model.dto.RegionDTO;
import com.kh.reactrip.place.model.dto.ThemeDTO;

public interface PlaceService {
	
	List<PlaceDTO> findAllPlace(String keyword, Long themeNo, Long regionNo, Integer page, Integer size, String sort);
	
	PlaceDTO findByTravelNo(String travelNo);
	
	List<RegionDTO> findAllRegion();
	
	List<ThemeDTO> findAllTheme();

}
