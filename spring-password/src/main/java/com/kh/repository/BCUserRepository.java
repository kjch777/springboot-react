package com.kh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.dto.BCUser;

/**
 * MyBatis 와 mapper 를 생략하고 작성하는 방법이다.
 * SQL 을 알아서 작성해준다.
 * **/
public interface BCUserRepository extends JpaRepository<BCUser, Integer> { // int 객체인 Integer 를 써주어야 한다.
	
	// save ◀ select 등을 이용하여 특정한 값을 검색하는 등의 행위를 하지 않는 한, 기본적인 작업(예를 들어 save) 의 SQL 쿼리문에 대한 기능은 작성하지 않는다.
	// BCUser saveUser();
	
	// ▼ 이메일 찾기 기능
	BCUser findByEmail(String email);
	// ▲ 이렇게만 작성해주어도 SQL 쿼리문이 알아서 실행된다. SELECT * FROM BCUser WHERE email = ?;
}
