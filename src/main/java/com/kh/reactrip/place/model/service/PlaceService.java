package com.kh.reactrip.place.model.service;

import java.util.List;

import com.kh.reactrip.place.model.dto.PlaceDTO;

public interface PlaceService {
	
	List<PlaceDTO> findAllPlace(String keyword, String theme, String region, Integer page, Integer size, String sort);

}
