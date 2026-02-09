package com.kh.reactrip.admin.travel.model.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.reactrip.admin.travel.model.dto.AdminTravelDTO;
import com.kh.reactrip.admin.travel.model.vo.AdminTravelVO;

@Mapper
public interface AdminTravelMapper {
	
	int getTotalCount();
	
	List<AdminTravelVO> findAllTravel(RowBounds rowBounds);
	
	int updateTravelStatus(AdminTravelVO vo);
	
	AdminTravelVO findByTravelNo(Long travelNo);
	
	int insertTravel(AdminTravelVO vo);
	
	AdminTravelDTO selectTravelDetail(Long travelNo);
	
	int updateTravel(AdminTravelDTO adminTravelDTO);
	
	List<AdminTravelVO> findNearbyTravels(Map<String, Double> params);
	
	int existsByTitle(String title);
	
	AdminTravelVO findByTitle(String title);
	int getSearchCount(String keyword);
	
	List<AdminTravelDTO> findBySearch(String keyword, RowBounds rowBounds);
	
	void insertTravelTheme(Map<String, Object> themeMap);
	void deleteTravelThemesByTravelNo(Long travelNo);

	List<Map<String, Object>> findAllRegions();
	
}