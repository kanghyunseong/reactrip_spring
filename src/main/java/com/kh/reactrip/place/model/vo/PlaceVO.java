package com.kh.reactrip.place.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder // 이미지가 없을수도 있음
@AllArgsConstructor
@ToString
public class PlaceVO {
	
	private Long travelNo; // TRAVEL_NO, 여행지순번, PK
	private String travelName; // TRAVEL_NAME, 여행지명
	private String travelContent; // TRAVEL_CONTENT, 여행지 설명
	private String travelImage; // TRAVLE_IMAGE, 여행지 사진
	private String travelStatus; // TRAVLE_STATUS, 여행지 삭제 여부, N = 삭제아님(존재함)
	private String travelAddress; // TRAVLE_ADDRESS, 주소
	private Double longitude; // MAP_X, 카카오맵 경도
	private Double latitude; // MAP_Y, 카카오맵 위도
	private Integer count; // COUNT, 조회수

}

/*
record로 구현할 경우에 사용하려고 미리 작성
public record PlaceVO(
		Long travelNo,
		String travelName,
		String travelContent,
		String travelImage,
		String travelStatus,
		String travelAddress,
		Double longitude,
		Double latitude,
		Integer count
		) {
	
}
*/
