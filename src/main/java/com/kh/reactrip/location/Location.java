package com.kh.reactrip.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Location {
	
	private Double longitude; // MAP_X, 카카오맵 경도
	private Double latitude; // MAP_Y, 카카오맵 위도

}
