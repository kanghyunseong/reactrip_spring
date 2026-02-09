package com.kh.reactrip.admin.travel.model.vo;

import com.kh.reactrip.admin.travel.model.dto.AdminTravelDTO;

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
public class AdminTravelVO {

	private Long travelNo;
    private Long regionNo;       // FK
    private String travelName;
    private String travelContent;
    private String travelImage;
    private String travelStatus;  // DEFAULT 'N'
    private String travelAddress;
    private Double mapX;
    private Double mapY;
    private Integer count;
    
    private String regionName;
    private String themeNames;  // TB_REG_THEME 매핑 테마명들 (쉼표 구분)
    private Long themeNo;       // 수정 폼용 단일 테마 (첫 번째 테마 번호)

    public AdminTravelVO(AdminTravelDTO dto) {
        this.travelNo = dto.getTravelNo();
        this.travelName = dto.getTravelName();
        this.travelAddress = dto.getTravelAddress();
        this.mapX = dto.getMapX();
        this.mapY = dto.getMapY();
        this.travelStatus = dto.getTravelStatus();
        this.travelImage = dto.getTravelImage();
        this.travelContent = dto.getTravelContent();
        this.regionNo = dto.getRegionNo();
        
    }
    
    
}
