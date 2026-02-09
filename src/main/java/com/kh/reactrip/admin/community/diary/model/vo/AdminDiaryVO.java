package com.kh.reactrip.admin.community.diary.model.vo;

import java.time.LocalDateTime;

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
public class AdminDiaryVO {

	private Long diaryNo;
	private String diaryTitle;
	private String diaryContent;
	private String diaryStatus;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private int count;
	private Long memberNo;
	private Long scheduleNo;
	private Long travelNo;
	private Long regionNo;
	private String regionName;
	private Long themeNo;
	private String themeName;
	private String memberName;
	private String email;

	public AdminDiaryVO(AdminDiaryDTO dto) {
        this.diaryNo = dto.getDiaryNo();
        this.diaryTitle = dto.getDiaryTitle();
        this.diaryContent = dto.getDiaryContent();
        this.diaryStatus = dto.getDiaryStatus();
        this.createdDate = dto.getCreatedDate();
        this.updatedDate = dto.getUpdatedDate();
        this.count = dto.getCount();
        this.memberNo = dto.getMemberNo();
        this.scheduleNo = dto.getScheduleNo();
        this.travelNo = dto.getTravelNo();
        this.regionNo = dto.getRegionNo();
        this.regionName = dto.getRegionName();
        this.themeNo = dto.getThemeNo();
        this.themeName = dto.getThemeName();
        this.memberName = dto.getMemberName();
        this.email = dto.getEmail();
    }
}
