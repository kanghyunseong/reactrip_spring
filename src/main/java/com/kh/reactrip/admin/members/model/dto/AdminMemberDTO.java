package com.kh.reactrip.admin.members.model.dto;

import java.sql.Date;

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
public class AdminMemberDTO {
	
	private Long memberNo;
	private String memberName;
	private String birthday;
	private String phone;
	private String email;
	private Date enrollDate;
	private String memberRole;
	private String image;

}
