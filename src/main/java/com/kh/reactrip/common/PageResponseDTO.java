package com.kh.reactrip.common;

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
public class PageResponseDTO<T> {
	
	private PageInfo pageInfo; 
    private List<T> data;

}
