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

	// 치킨 메뉴 수정하기 / id: 수정할 칼럼의 아이디 값, uc: 수정된 내용을 저장할 치킨 객체
	// findById 를 작성해줄 때는, 아이디를 찾지 못하는 예외사항을 필수로 작성해주어야 한다.
	// ▶ .orElseThrow() 로 예외사항 작성
	public Chicken updateChicken(Integer id, Chicken uc) {
		Chicken chicken = chickenRepository.findById(id)
						  .orElseThrow(() -> new RuntimeException("일치하는 정보를 찾을 수 없습니다."));
		
		// 치킨 객체에 수정된 치킨 이름을 가져와서 넣어주기
		chicken.setChickenName(uc.getChickenName());
		chicken.setDescription(uc.getDescription());
		chicken.setPrice(uc.getPrice());
		
		return chickenRepository.save(chicken);
	}
	
	// 치킨 메뉴 삭제하기
	public void deleteChicken(Integer id) {
		Chicken chicken = chickenRepository.findById(id)
						  .orElseThrow(() -> new RuntimeException("일치하는 정보를 찾을 수 없습니다."));
		chickenRepository.delete(chicken);
	}
	
	// 치킨 메뉴 검색하기
	public List<Chicken> searchChickens(String query) {
		return chickenRepository.findByChickenNameContainingIgnoreCase(query);
	}

}
