package com.kh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringXmlApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringXmlApiApplication.class, args);
	}

}

/**
 * JPA: Java 에서 DB 를 조작할 수 있게 해주고, 기본 쿼리를 보유하면서
 * 		mapper, mybatis 를 사용하지 않아도 되게 해준다. 
 * **/