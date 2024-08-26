package com.kh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kh.entity.KH_pizza;

@Repository // mapper 와 mybatis 를 합쳐놓은 기능으로, 기본적으로 select, insert, 특정 단어 찾기 정도는
// JpaRepository 안에 작성이 되어있기 때문에, 이 인터페이스 안에는 검색같은 특수한 작업만 작성해주어도 된다.
// mapper 와 동일하게 interface 이다.
// mapper 와 다른 점은 mybatis 와 같은 기능을 이미 포함한 interface 라는 것이다.
public interface PizzaRepository extends JpaRepository<KH_pizza, Integer> {

	// 검색같이 보이는 결과물이 여러개일 때는 리스트를 사용한다.
	// findByPizzaName == where pizza_name = #{pizzaName} 과 같다.
	// Containing == Like % % ◀ 특정 단어를 앞 뒤 글자와 상관없이 검색하는 것
	// Like %name ◀ 문자열이 name 으로 끝나는 모든 값 찾기
	// Like %name% ◀ 문자열 중간에 name 이 들어가는 모든 값 찾기
	// Like name% ◀ 문자열이 name 으로 시작하는 모든 값 찾기
	// IgnoreCase ◀ 대/소문자 구분 X1`
	List<KH_pizza> findByPizzaNameContainingIgnoreCase(String query);
}
