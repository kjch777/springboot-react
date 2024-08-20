package com.kh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kh.dto.Chicken;

@Repository// MyBatis - mapper 2가지를 설정하는 곳
// @Repository @Mapper 는 interface 로 시작한다.
public interface ChickenRepository extends JpaRepository<Chicken, Integer> {
	// 전체 보기/넣기/수정하기/삭제하기 와 같은 기본 기능은 JpaRepository 안에 모두 들어있다.
}
