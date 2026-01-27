package com.kh.reactrip.admin.community.comment.model.dto;

import java.time.LocalDateTime;

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
public class AdminCommentDTO {
	
	private Long commentNo;
	private String commentStatus;
	private String commentContent;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private Long diaryNo;
	private Long memberNo;
	private String memberName;
}
