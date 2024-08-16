package com.kh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class APIService {

	// @Value 를 활용하여 applicaion.properties 에 작성한 값 가져오기
	
	@Value("${api.base-url}")
	private String baseUrl;
	
	@Value("{api.key}")
	private String apiKey;
	
	@Value("${api.content-type}")
	private String contentType;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// 공공 데이터를 가져오기 위한 환경설정 서비스 메서드 
	public String getAPIData(String endPoint) {
		// url = 시작 주소 + 상세 주조
		String url = baseUrl + endPoint; // 시작 주소와 API 가 끝나는 지점(endPoint) 
		
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Type", contentType);
		header.set("Authorization", "Bearer " + apiKey); // Bearer 뒤에 반드시 띄어쓰기 까지 해야 한다.
		
		HttpEntity<String> entity = new HttpEntity<>(header);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		return response.getBody();
	}
}
