package com.kh.reactrip.place.model.dto;

public class PlaceDTO {
	
	private Long travelNo; // TRAVEL_NO, 여행지순번, PK
	private String travelName; // TRAVEL_NAME, 여행지명
	private String travelContent; // TRAVEL_CONTENT, 여행지 설명
	private String travelImage; // TRAVLE_IMAGE, 여행지 사진
	private String travelStatus; // TRAVLE_STATUS, 여행지 삭제 여부, N = 삭제아님(존재함)
	private String travelAddress; // TRAVLE_ADDRESS, 주소
	private Double longitude; // MAP_X, 카카오맵 경도
	private Double latitude; // MAP_Y, 카카오맵 위도
	private Integer count; // COUNT, 조회수
	
	// 지역 JOIN
	private Long regionNo; // REGION_NO, 지역번호, PK, Travel 테이블의 FK
	private String regionTitle; // REGION_NAME, 지역이름
	
	// 테마 JOIN
	private Long themeNo; // THEME_NO, 테마번호, PK, Travel 테이블의 FK 아님
	private String themeTitle; // THEME_NAME, 테마이름

}
