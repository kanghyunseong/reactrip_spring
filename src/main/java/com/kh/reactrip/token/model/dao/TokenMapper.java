package com.kh.reactrip.token.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.reactrip.token.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {
	  
    void deleteTokenByUserNo(Long authNo);
    
    void saveToken(RefreshToken refreshToken);
    
    RefreshToken findByToken(String token);
}
