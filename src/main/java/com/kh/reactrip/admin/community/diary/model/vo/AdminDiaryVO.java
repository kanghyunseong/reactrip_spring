package com.kh.reactrip.admin.community.diary.model.vo;

import java.time.LocalDateTime;

import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDTO;
import com.kh.reactrip.admin.community.diary.model.dto.AdminDiaryDetailDTO;

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
	private String memberName;
	private String email;

	public AdminDiaryVO(AdminDiaryDTO dto) {
		this.diaryNo = dto.getDiaryNo();
		this.diaryTitle = dto.getDiaryTitle();
		this.diaryStatus = dto.getDiaryStatus();
		this.createdDate = dto.getCreatedDate();
		this.count = dto.getCount();
		this.travelNo = dto.getTravelNo();
		this.memberName = dto.getMemberName();
		
		if (dto instanceof AdminDiaryDetailDTO detail) {
            this.diaryContent = detail.getDiaryContent();
            this.updatedDate = detail.getUpdatedDate();
            this.memberNo = detail.getMemberNo();
            this.scheduleNo = detail.getScheduleNo();
        }
	}
}
