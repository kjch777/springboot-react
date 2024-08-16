package com.kh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.service.APIService;

/**
 * 공공 데이터 API 활용 Controller
 * **/
@RestController
public class APIController {

	@Autowired
	private APIService apiService;
	
	@GetMapping("/api/dataService")
	public String dataAPI() {
		String endPoint = "/끝나는 주소";
		return apiService.getAPIData(endPoint);
	}
}
