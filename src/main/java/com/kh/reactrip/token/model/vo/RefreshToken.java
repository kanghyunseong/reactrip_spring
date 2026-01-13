package com.kh.reactrip.token.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {
	
	private String token;
	private Long userNo;
	private Long expiration;

}
