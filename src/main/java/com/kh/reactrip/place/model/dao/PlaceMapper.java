package com.kh.reactrip.place.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.place.model.dto.PlaceDTO;
import com.kh.reactrip.place.model.dto.RegionDTO;
import com.kh.reactrip.place.model.dto.ThemeDTO;

@Mapper
public interface PlaceMapper {
	
	List<PlaceDTO> findAllPlace(Map<String, Object> query, RowBounds rb);
	
	PlaceDTO findByTravelNo(Long travelNo);
	
	List<RegionDTO> findAllRegion();
	
	List<ThemeDTO> findAllTheme();

}
