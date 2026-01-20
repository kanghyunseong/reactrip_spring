package com.kh.reactrip.place.model.service;

import java.util.List;

import com.kh.reactrip.place.model.dto.PlaceDTO;

public interface PlaceService {
	
	List<PlaceDTO> findAllPlace(String keyword, Long themeNo, Long regionNo, Integer page, Integer size, String sort);

}
