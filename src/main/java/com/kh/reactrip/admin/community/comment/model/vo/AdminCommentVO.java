package com.kh.reactrip.admin.community.comment.model.vo;

import java.time.LocalDateTime;

import com.kh.reactrip.admin.community.comment.model.dto.AdminCommentDTO;
<<<<<<< HEAD
import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
=======
>>>>>>> 6c4d14ea81831215e7ed33a0c260c740121d4425

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
	    this.commentStatus = dto.getCommentStatus();
	    this.commentContent = dto.getCommentContent();
	    this.createdDate = dto.getCreatedDate();
	    this.updatedDate = dto.getUpdatedDate();
	    this.diaryNo = dto.getDiaryNo();
	    this.memberNo = dto.getMemberNo();
	    this.memberName = dto.getMemberName();
	}

}
