package com.kh.reactrip.place.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.place.model.dto.PlaceDTO;

@Mapper
public interface PlaceMapper {
	
	List<PlaceDTO> findAllPlace(@Param("query") Map<String, Object> query, RowBounds rb);

}
