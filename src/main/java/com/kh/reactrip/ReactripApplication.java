package com.kh.reactrip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kh.reactrip")
public class ReactripApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactripApplication.class, args);
	}

}
