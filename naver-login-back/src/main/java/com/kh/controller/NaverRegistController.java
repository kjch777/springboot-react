package com.kh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.dto.NaverUser;
import com.kh.service.NaverUserService;

// 네이버 회원가입 후, DB 에 회원가입 정보를 등록하는 컨트롤러
@RestController
public class NaverRegistController {

	@Autowired
	private NaverUserService naverUserService;
	
	// 회원가입을 하기 위한 Post Mapping 작성하기
	@PostMapping("/naverAPI/register") // Java(BackEnd) 와 React(FrontEnd) 가 서로 만나는 장소
	public String insertNaverUser(@RequestBody NaverUser naverUser) {
		// React 로 가져온 naverUser 정보를, 수정 없이 DB 에 그대로 넣겠다.
		naverUserService.insertNaverUser(naverUser);
		
		// naverUserService.insertNaverUser(null);
		// ▲ null 이 들어간 자리에는, React 에서 받아온 값을 넣어주면 된다.
		// 맨 처음에는 Java 에서 어떤 값을 넣어주어야 할지 모르기 때문에, null 로 설정이 되어있는 것일 뿐이다.
		// null 이 들어간 자리에는 @RequestBody 또는 @RequestParam 으로 가져온 값을 작성해주면 된다.
		// @RequestBody == 전체(= 전체를 한 번에 집어넣는다는 것은, 부분 수정이 필요하지 않다는 것이다.) 
		// @RequestParam == 부분적으로 수정 또는 추가 할 때 사용하면 된다.
		return "Naver API 를 사용한 회원가입에 성공하였습니다.";
	}
}
