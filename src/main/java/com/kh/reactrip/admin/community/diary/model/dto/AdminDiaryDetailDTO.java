package com.kh.reactrip.admin.community.diary.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryImageVO;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDiaryDetailDTO extends AdminDiaryDTO{

	private String diaryContent;
    private Long memberNo;
    private Long scheduleNo;
    private LocalDateTime updatedDate;
    private List<AdminDiaryImageVO> images;

    public AdminDiaryDetailDTO(AdminDiaryVO vo, List<AdminDiaryImageVO> images) {
        this.diaryContent = vo.getDiaryContent();
        this.memberNo = vo.getMemberNo();
        this.scheduleNo = vo.getScheduleNo();
        this.updatedDate = vo.getUpdatedDate();
        this.images = images;
    }
}
