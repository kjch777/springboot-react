package com.kh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 공공 데이터 API 를 이용한 API URL 주소 값 한번 더 확인하기
// http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty

// 공공 데이터 API 주소 http://apis.data.go.kr/ ◀ React 에서 .env 로 port=80 처리를 해준 것
// 내 컴퓨터 주소 http://localhost:포트번호/ ◀ React 에서 .env 로 port=80 처리를 해주지 않은 것
@RequestMapping("/B552584/ArpltnInforInqireSvc") // 공공 데이터에서 대기 오염 정보 API 공통 주소
// 만약, 대기 오염 정보가 아니라 수질 오염 정보를 이용할 것이라면
// @RequestMapping("/수질오염정보API주소")
@RestController
public class APISController {
	
	// \특수문자 ◀ 특수문자를 글자 취급한다.

	@GetMapping("/getCtprvnRltmMesureDnsty") // 시도별 실시간 측정 정보 조회 API 주소
	public String getCtprvnRltmMesureDnsty() {
		return "";
	}
	
	@GetMapping("/getMsrstnAcctoRltmMesureDnsty") // 측정소별 실시간 측정 정보 조회
	public String getMsrstnAcctoRltmMesureDnsty() {
		return "";
	}
	
	@GetMapping("/getUnityAirEnvrnIdexSnstiveAboveMsrstnList") // 통합 대기 환경 지수 나쁨 이상 측정소 목록 조회
	public String getUnityAirEnvrnIdexSnstiveAboveMsrstnList() {
		return "";
	}
	
	@GetMapping("/getMinuDustFrcstDspth") // 대기질 예보통보 조회
	public void 예보통보조회() {
		System.out.println("결과 전달");
	}
	
	@GetMapping("/getMinuDustWeekFrcstDspth") // 초미세먼지 주간예보 조회
	public void 주간예보조회() {
		System.out.println("결과 전달");
	}
}
