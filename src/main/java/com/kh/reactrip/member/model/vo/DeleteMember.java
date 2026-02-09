package com.kh.reactrip.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMember {
	
	private Long deletedNo;
	private Long memberNo;
	private Date deletedDate;
	private char deleteStatus;
}
