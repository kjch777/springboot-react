package tosspay.controller;

import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// 인증 키 확인 후 전송하는 코드
@RestController // import 에 문제가 있을 경우 ▶ refresh gradle project
public class AuthorizationController {

	// SpringBoot 에서, application.properties 에 작성한 값을 가져오기 위해
	// @Value 라는 어노테이션을 사용한다.
	// @Value("${application.properties 에 작성해둔 변수명}")
	// import org.springframework.beans.factory.annotation.Value; ◀ lombok 아님!
	@Value("${apiSecretKey}")
	private String apiSecretKey; // 가져온 값을 담을 변수(그릇) 설정
	
	// HTTP 요청을 보내기 위해, 요청 보낼 값을 담을 공간 생성하기
	private final RestTemplate restTemplate = new RestTemplate();
	
	
	
	// const encryptedWidgetSecretKey = "Basic " + Buffer.from(widgetSecretKey + ":").toString("base64");
	// const encryptedApiSecretKey = "Basic " + Buffer.from(apiSecretKey + ":").toString("base64");
	
	// 주어진 secretKey 를, Base64 로 인코딩하여, HTTP 헤더에 secretKey 를 가져갈 수 있도록 설정하기
	/**
	 * Base64: 사람이 작성한 데이터를 컴퓨터가 읽을 수 있는 TEXT 형식으로 변환하는 방법이다.
	 * 예를 들어, Hello ▶ Base64 이용 ▶ 컴퓨터가 읽을 수 있도록 인코딩
	 * **/
	private String encodeSecretKey(String secretKey) {
		return "Basic" + new String(Base64.getEncoder().encode((secretKey + ":").getBytes()));
	}
	
	/**
	 * app.get("/callback-auth", function (req, res) {
  	   	const { customerKey, code } = req.query;
  		fetch("https://api.tosspayments.com/v1/brandpay/authorizations/access-token", {
    		method: "POST",
    		headers: {
    			Authorization: encryptedApiSecretKey,
      			"Content-Type": "application/json",
	 * **/
	@GetMapping("/callback-auth") // app.get("/callback-auth"
	// function (req, res) { const { customerKey, code } = req.query;
	// function 기능명은 생략가능하여 없을뿐 (req, res) { const { customerKey, code } = req.query;
	// java 는 기능명이 존재해야 한다.
	// function callbackAuth (req, res) { const { customerKey, code } = req.query;
	public ResponseEntity<?> callbackAuth(@RequestParam String customerKey, @RequestParam String code) {
		// fetch("https://api.tosspayments.com/v1/brandpay/authorizations/access-token", {
		String url = "https://api.tosspayments.com/v1/brandpay/authorizations/access-token";
		HttpHeaders headers = new HttpHeaders(); // import org.springframework.http.HttpHeaders;
		
		/**
		 * Authorization: encryptedApiSecretKey,
      	   "Content-Type": "application/json",
		 * **/
		headers.set("Authorization", encodeSecretKey(apiSecretKey));
		headers.set("Content-Type", "application/json");
		
		/**
		 * body: JSON.stringify({
      		grantType: "AuthorizationCode",
      		customerKey,
      		code,
    	   }),
		 * **/
		// body: JSON.stringify({
		// Map.of() ◀ Map 객체 안에 있는 of 라는 기능이다.
		// of 는 가져온 값을 추가하거나 제거할 수 없도록 설정하여, 가져온 값이 오염되지 않도록 보호하는 것이다.
		// Map<String, String> requestBody = Map.of(
	    // "key1", "value1",
	    // "key2", value2,
	    // "key3", value3);
		Map<String, String> requestBody = Map.of(
				      "grantType", "AuthorizationCode",
				      "customerKey", customerKey,
				      "code", code);
		
		// HttpEntity 객체를 생성하여, HTTP 요청의 본문과, 요청 조건 사항이 담긴 headers 를 가져와, 한 번에 묶어서 전달 할 예정이다.
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
		// }).then(async function (response) {
		
		/**
		 * ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
		 * url: 요청을 보낼 주소 값 가져오기
		 * HttpMethod.POST: 값을 어떻게 해야하는지(삽입/조회/수정/삭제) 전달하는 것
		 * entity: 코드를 작성한 목적이 담긴 내용물과 제목/요청/조건 사항이 담긴 내용
		 * Map.class: 응답 받을 데이터 타입 지정 ▶ 응답을 key-value 로 받아, 가지고 있겠다는 것
		 * **/
		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
		// const result = await response.json();
		// res.status(response.status).json(result);
		
		// 응답에 대한 성공/실패 결과가 담긴 내용을 전달하는 것
		return new ResponseEntity<>(response.getBody(), response.getStatusCode());
	}
	
}
