package com.kh.reactrip.place.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {
	
	private Long regionNo; // REGION_NO, 지역번호, PK, Travel 테이블의 FK
	private String regionName; // REGION_NAME, 지역이름

}
