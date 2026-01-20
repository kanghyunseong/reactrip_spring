package com.kh.reactrip.admin.community.comment.model.dto;

import java.time.LocalDateTime;

import com.kh.reactrip.admin.community.comment.model.vo.AdminCommentVO;

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
	private String commentContent;
	private LocalDateTime createdDate;
	private Long diaryNo;
	private String memberName;
	
	public AdminCommentDTO(AdminCommentVO vo) {
		this.commentNo = vo.getCommentNo();
		this.commentContent = vo.getCommentContent();
		this.createdDate = vo.getCreatedDate();
		this.diaryNo = vo.getDiaryNo();
		this.memberName = vo.getMemberName();
	}
}
