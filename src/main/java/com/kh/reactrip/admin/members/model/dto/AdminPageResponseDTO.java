package com.kh.reactrip.admin.members.model.dto;

import java.util.List;
import com.kh.reactrip.util.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminPageResponseDTO {
	
	private PageInfo pageInfo;
	private List<AdminMemberDTO> members;

}
