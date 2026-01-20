package com.kh.reactrip.admin.community.comment.model.dto;

import java.time.LocalDateTime;

import com.kh.reactrip.admin.community.comment.model.vo.AdminCommentVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminCommentDetailDTO extends AdminCommentDTO {
    private Long memberNo;
    private LocalDateTime updatedDate;
    private String commentStatus;

    public AdminCommentDetailDTO(AdminCommentVO vo) {
        super(vo);
        this.memberNo = vo.getMemberNo();
        this.updatedDate = vo.getUpdatedDate();
        this.commentStatus = vo.getCommentStatus();
    }
}