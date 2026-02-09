package com.kh.reactrip.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private Long memberNo;
	private String memberId;
	private String memberName;
	private String birthDay;
	private String phone;
	private String email;
    private String image; 
    private String memberRole;
    private Date enrollDate;

}
