package com.kh.reactrip.token.model.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.reactrip.token.model.vo.RefreshToken;

@Mapper
public interface TokenMapper {

	  
    @Delete("DELETE FROM TB_TOKEN WHERE USER_NO = #{userNo}")
    void deleteTokenByUserNo(Long userNo);
    
  
    @Insert("INSERT INTO TB_TOKEN (TOKEN, USER_NO, EXPIRATION) " +
            "VALUES (#{token}, #{userNo}, #{expiration})")
    void saveToken(RefreshToken refreshToken);
    
   
    @Select("SELECT TOKEN as token, USER_NO as userNo, EXPIRATION as expiration " +
            "FROM TB_TOKEN WHERE TOKEN = #{token}")
    RefreshToken findByToken(String token);
}
