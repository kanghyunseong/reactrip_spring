package com.kh.reactrip.admin.community.diary.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryVO;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryImageVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminDiaryDTO {
    
    private Long diaryNo;
    private String diaryTitle;
    private String diaryContent;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int count;
    private String diaryStatus;
    private Long memberNo;
    private Long scheduleNo;
    private Long travelNo;
    private Long regionNo;
    private String regionName;
    private Long themeNo;
    private String themeName;
    private String memberName;
    private String email;
    private List<AdminDiaryImageVO> images;
    
    public AdminDiaryDTO(AdminDiaryVO vo) {
        this.diaryNo = vo.getDiaryNo();
        this.diaryTitle = vo.getDiaryTitle();
        this.diaryContent = vo.getDiaryContent();
        this.createdDate = vo.getCreatedDate();
        this.updatedDate = vo.getUpdatedDate();
        this.count = vo.getCount();
        this.diaryStatus = vo.getDiaryStatus();
        this.memberNo = vo.getMemberNo();
        this.scheduleNo = vo.getScheduleNo();
        this.travelNo = vo.getTravelNo();
        this.regionNo = vo.getRegionNo();
        this.regionName = vo.getRegionName();
        this.themeNo = vo.getThemeNo();
        this.themeName = vo.getThemeName();
        this.memberName = vo.getMemberName();
        this.email = vo.getEmail();
    }
}