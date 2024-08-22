package com.kh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dto.Chicken;
import com.kh.repository.ChickenRepository;

@Service
public class ChickenService {

	@Autowired
	private ChickenRepository chickenRepository;
	
	// 치킨 테이블 모두 보기 ◀ 모두 보는 것은 List(목록)
	// 어떤 목록을 볼 것인가? List<Chicken> ◀ 목록<주제>
	public List<Chicken> getAllChickens() {
		return chickenRepository.findAll(); // 모두 찾기같은 기능은 기본으로 내장되어 있다.
	}
	
	// 치킨 메뉴 추가하기
	public Chicken createChicken(Chicken chicken) {
		return chickenRepository.save(chicken); // 치킨에 대하여, DTO 에 작성된 칼럼들에 모두 삽입
	}

	// 치킨 메뉴 상세보기
	public Chicken findById(Integer id) {
		return chickenRepository.findById(id)
			   .orElseThrow(() -> new RuntimeException("일치하는 정보를 찾을 수 없습니다."));
	}
}
