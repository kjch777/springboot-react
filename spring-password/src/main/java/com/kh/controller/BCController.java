package com.kh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.dto.BCUser;
import com.kh.service.BCUserService;

@RestController
public class BCController {

	@Autowired
	private BCUserService bcUserService;
	
	@PostMapping("/api/register")
	public String saveUser(@RequestBody BCUser bcUser ) {
		bcUserService.saveUser(bcUser);
		return "회원가입이 성공적으로 완료됐습니다.";
	}
}
