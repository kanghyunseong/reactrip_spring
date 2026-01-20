package com.kh.reactrip.admin.community.comment.model.vo;

import java.time.LocalDateTime;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDetailDTO;
import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AdminCommentVO {
	
	private Long commentNo;
	private String commentStatus;
	private String commentContent;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private Long diaryNo;
	private Long memberNo;
	
	private String memberName;
	
	public AdminCommentVO(AdminCommentDTO dto) {
	    this.commentNo = dto.getCommentNo();
	    this.commentContent = dto.getCommentContent();
	    this.createdDate = dto.getCreatedDate();
	    this.memberName = dto.getMemberName();
	    
	    if (dto instanceof AdminCommentDetailDTO detail) {
	        this.updatedDate = detail.getUpdatedDate();
	        this.diaryNo = detail.getDiaryNo();
	        this.memberNo = detail.getMemberNo();
	        this.commentStatus = detail.getCommentStatus();
	    }
	}

}
