package com.kh.reactrip.token.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "token.refresh")
public class TokenProperties {
    private Long expirationMillis;  // Refresh Token 만료 시간 (밀리초)
}