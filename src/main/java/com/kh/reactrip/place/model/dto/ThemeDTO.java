package com.kh.reactrip.place.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDTO {
	
	private Long themeNo; // THEME_NO, 테마번호, PK, Travel 테이블의 FK 아님
	private String themeName; // THEME_NAME, 테마이름

}
