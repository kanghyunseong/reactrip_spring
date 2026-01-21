package com.kh.reactrip.diary.model.vo;

import java.sql.Date;
import java.util.List;

import com.kh.reactrip.diary.model.dto.DiaryImageDTO;

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
public class DiaryDetailVO {

	private int diaryNo;			// 게시글 고유번호
	private String diaryTitle; 		// 게시글 제목
	private String diaryContent;	// 게시글 내용
	private String createdDate; 	// 게시글 등록일
	private String updatedDate; 	// 게시글 수정일
	private int count;				// 조회수
	private int memberNo;			// 회원 고유번호
	private String memberName;       // 작성자이름           
	private int scheduleNo;			// 일정 번호
	private int travelNo;			// 여행지 번호
	private int regionNo;
	private String regionName;
	private int themeNo;
	private String themeName;
	
	private List<DiaryImageDTO> diaryImageDTO;

}
