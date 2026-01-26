package com.kh.reactrip.token.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.reactrip.auth.model.dto.MemberLoginDTO;
import com.kh.reactrip.token.model.vo.RefreshToken;

import jakarta.validation.Valid;

@Mapper
public interface TokenMapper {
	  
    void deleteTokenByUserNo(Long authNo);
    
    void saveToken(RefreshToken refreshToken);
    
    RefreshToken findByToken(String token);

    @Delete("DELETE FROM TB_TOKEN WHERE MEMBER_ID = #{memberId} AND TOKEN = #{refreshToken}")
	int deleteTokenForLogout(@Valid MemberLoginDTO member);
	
}
