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
    private Long diaryNo;
    private LocalDateTime updatedDate;

    public AdminCommentDetailDTO(AdminCommentVO vo) {
        super(vo); // 공통 필드는 부모가 처리
        this.memberNo = vo.getMemberNo();
        this.diaryNo = vo.getDiaryNo();
        this.updatedDate = vo.getUpdatedDate();
    }
}