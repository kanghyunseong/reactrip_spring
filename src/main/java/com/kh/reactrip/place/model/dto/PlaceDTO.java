package com.kh.reactrip.place.model.dto;

import com.kh.reactrip.location.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceDTO {
	
	private Long travelNo; // TRAVEL_NO, 여행지순번, PK
	private String travelName; // TRAVEL_NAME, 여행지명
	private String travelContent; // TRAVEL_CONTENT, 여행지 설명
	private String travelImage; // TRAVLE_IMAGE, 여행지 사진
	private String travelStatus; // TRAVLE_STATUS, 여행지 삭제 여부, N = 삭제아님(존재함)
	private String travelAddress; // TRAVLE_ADDRESS, 주소
	private Integer count; // COUNT, 조회수
	
	// 지역 JOIN
	private Long regionNo; // REGION_NO, 지역번호, PK, Travel 테이블의 FK
	private String regionName; // REGION_NAME, 지역이름
	
	// 테마 JOIN
	private Long themeNo; // THEME_NO, 테마번호, PK, Travel 테이블의 FK 아님
	private String themeName; // THEME_NAME, 테마이름
	
	// Locaion 객체
	private Location location;

}
