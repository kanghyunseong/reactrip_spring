package com.kh.reactrip.token.model.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;
	private SecretKey key;
	
	@PostConstruct
	public void init() {
		// log.info("{}", secretKey);
		byte[] arr = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(arr);
	}
	
	public String getAccessToken(String username, String role) {
		return Jwts.builder()
				   .subject(username)
				   .claim("role", role)
				   .issuedAt(new Date())
				   .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60  * 24))) // 만료일
				   .signWith(key) // 서명
				   .compact();
	}
	
	public String getRefreshToken(String username) {
		return Jwts.builder()
				   .subject(username)
				   .issuedAt(new Date())
				   // .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3 )))
				   // .expiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.ToMillis(10)))
				   .expiration(Date.from(Instant.now().plus(Duration.ofDays(3))))
				   .signWith(key)
				   .compact();
	}
	public Claims parseJwt(String token) {
		return Jwts.parser()
				   .verifyWith(key)
				   .build()
				   .parseSignedClaims(token)
				   .getPayload();
	}
	
}
