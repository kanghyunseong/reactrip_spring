package com.kh.reactrip.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthMember {
	private Long authNo;
	private String memberId;
	private String memberPwd;
	private Long memberNo;
}
