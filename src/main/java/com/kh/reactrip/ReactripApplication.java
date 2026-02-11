package com.kh.reactrip;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync

@MapperScan("com.kh.reactrip.")
public class ReactripApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactripApplication.class, args);
	}

}
