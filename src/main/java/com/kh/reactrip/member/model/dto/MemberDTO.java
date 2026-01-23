package com.kh.reactrip.member.model.dto;

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
public class MemberDTO {
	private String memberName;
	private String birthDay;
	private String phone;
	private String image;
	private String memberPwd;
}
