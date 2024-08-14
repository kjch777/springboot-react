package com.kh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.dto.NaverUser;
import com.kh.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	/**
	 * 로그인 했을 때 로그인 값이 
	 * total: 1 ◀ 로그인 정보를 조회했을 때 존재하는 칼럼을 1개 발견했기 때문에 1
	 * total: 0 ◀ 맞지 않는 아이디/비밀번호를 입력했을 때는 존재하는 칼럼을 발견하지 못했기 때문에 0
	 * **/
	@PostMapping("/login")
	// @RequestBody Map<String, String> loginData
	public ResponseEntity<String> login(@RequestParam("id") String id, @RequestParam("password") String password) {
		NaverUser naverUser = loginService.login(id, password);
		if(naverUser != null) { // naverUser 정보가 존재한다면 null 이 아니다.
			return ResponseEntity.ok("로그인 성공");
		} else { // naverUser 정보가 없을 때
			// ResponseEntity.status ◀ DB 또는 어떤 값에 대한 결과 상태
			// HttpStatus ◀ GET, POST 와 같은 메서드 기능이 정상적으로 동작했는지
			// UNAUTHORIZED ◀ 인증 실패(주로 로그인 실패)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
		}
	}
}
