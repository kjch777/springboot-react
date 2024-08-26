package com.kh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kh.dto.Chicken;

@Repository// MyBatis - mapper 2가지를 설정하는 곳
// @Repository @Mapper 는 interface 로 시작한다.
public interface ChickenRepository extends JpaRepository<Chicken, Integer> {
	// 검색은 SQL 쿼리문이 예외적이기 때문에, 따로 필수적으로 작성해주어야 한다.
	/**
	 * findByChickenName: 어떤 칼럼에서 검색할 것인지, WHERE chickenName = "?" 에 해당한다.
	 * Containing: 부분 일치를 허용해주는 것으로, Like %...% 에 해당한다.
	 * IgnoreCase: 대/소문자 구분없이 검색하는 것(사용하는 법은 같다.)
	 * **/
	List<Chicken> findByChickenNameContainingIgnoreCase(String query);
	
	// 전체 보기/넣기/수정하기/삭제하기 와 같은 기본 기능은 JpaRepository 안에 모두 들어있다.(SELECT * FROM chicken 등)
	
	// 특정 값을 찾을 때 쓰는 기능
	// findById(Integer id); ◀ where 대신 find 를 쓴 것
	// 만약, where 절로 이메일 = '' 비밀번호 = '' ◀ 둘 다 사용하여 로그인 할 때
	// Repository 에 findByEmailPassword 를 작성해준다.(where 절과 같은 기능)
}
