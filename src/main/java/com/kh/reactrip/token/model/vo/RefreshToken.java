package com.kh.reactrip.token.model.vo;

import lombok.Builder;
import lombok.Value;
@Value
@Builder
public class RefreshToken {
	
	private Long tokenNo;
	private String token;
	private Long authNo;
	private Long expiration;

}
