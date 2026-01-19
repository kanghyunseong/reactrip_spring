package com.kh.reactrip.token.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.reactrip.token.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {

	  
    @Delete("DELETE FROM TB_TOKEN WHERE AUTH_NO = #{authNo}")
    void deleteTokenByUserNo(Long authNo);
    
  
    @Insert("INSERT INTO TB_TOKEN (TOKEN_NO, TOKEN, AUTH_NO, EXPIRATION) " +
            "VALUES (SEQ_TOKEN_NO.NEXTVAL, #{token}, #{authNo}, #{expiration})")
    void saveToken(RefreshToken refreshToken);
    
   
    @Select("SELECT TOKEN_NO as tokenNo, TOKEN as token, AUTH_NO as authNo, EXPIRATION as expiration " +
            "FROM TB_TOKEN WHERE TOKEN = #{token}")
    RefreshToken findByToken(String token);
}
