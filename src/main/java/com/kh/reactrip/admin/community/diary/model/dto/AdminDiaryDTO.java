package com.kh.reactrip.admin.community.diary.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryImageVO;
import com.kh.reactrip.admin.community.diary.model.vo.AdminDiaryVO;

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
public class AdminDiaryDTO {
	
	private Long diaryNo;
	private String diaryTitle;
	private LocalDateTime createdDate;
	private int count;
	private Long travelNo;
	private String memberName; 
    private String diaryStatus;
    
    public AdminDiaryDTO(AdminDiaryVO vo) {
        this.diaryNo = vo.getDiaryNo();
        this.diaryTitle = vo.getDiaryTitle();
        this.createdDate = vo.getCreatedDate(); 
        this.count = vo.getCount();
        this.travelNo = vo.getTravelNo();
        this.memberName = vo.getMemberName();
        this.diaryStatus = vo.getDiaryStatus();
    }

}
