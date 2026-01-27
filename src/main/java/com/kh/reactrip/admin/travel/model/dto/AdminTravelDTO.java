package com.kh.reactrip.admin.travel.model.dto;

import com.kh.reactrip.admin.travel.model.vo.AdminTravelVO;

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
public class AdminTravelDTO {

	private Long travelNo;
    private String travelName;
    private String travelAddress;
    private Double mapX; // 위도
    private Double mapY; // 경도
    private String travelStatus; // 'Y' or 'N'
    private Integer count; // 조회수
    private String travelImage; // 이미지 경로
    private String regionName; // 지역명 (TB_REGION과 JOIN해서 가져올 것)
    private String travelContent;
    private Long regionNo;
    
    public AdminTravelDTO(AdminTravelVO vo) {
        this.travelNo = vo.getTravelNo();
        this.travelName = vo.getTravelName();
        this.travelAddress = vo.getTravelAddress();
        this.mapX = vo.getMapX();
        this.mapY = vo.getMapY();
        this.travelStatus = vo.getTravelStatus();
        this.count = vo.getCount();
        this.travelImage = vo.getTravelImage();
        this.regionName = vo.getRegionName();
        this.travelContent = vo.getTravelContent();
        this.regionNo = vo.getRegionNo();
    }
}
