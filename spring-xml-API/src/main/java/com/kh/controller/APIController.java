package com.kh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.service.APISService;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	private APISService apisService;
	
	/*
	 	▼ return 으로 오직 전달만 진행할 때 사용
		@GetMapping("/air-pollution")
		public String getAirData() throws Exception {
			return apisService.getAirAPIData();
		}
	*/
	
	@GetMapping("/air-pollution")
	public String getAirData() throws Exception {
		String airData = apisService.getAirAPIData();
		System.out.println("Air Data: " + airData);
		return airData;
	}
	
	// 디버그: 문제가 발생하거나 특정 값을 확인할 때 사용한다.
}
