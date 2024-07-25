package tosspay.controller;

import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // html 파일이 아니라, url 주소 값으로 연동해주어야 한다.
@RequestMapping("/confirm")
public class PaymentController {

	// application.properties 에 설정 key 명칭을 가져오기 위해 value 사용하기
	@Value("${widgetSecretKey}") // 특정한 key 명칭을 외부(다른 곳) 에서 가져와 사용할 때는 ${key이름} 형식으로 작성해준다.
	private String widgetSecretKey;
	
	@Value("${apiSecretKey}")
	private String apiSecretKey;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	private String encodeSecretKey(String secretKey) {
		return "Basic" + new String(Base64.getEncoder().encode((secretKey + ":").getBytes()));
	}
	
	// widget 이라는 주소로 결제 정보가 들어오면, 결제 확인 창구로 결제 정보와 결제 주체(사용자) 의 비밀번호를 넘겨주는 것이다.
	// widget 은 payment, brandpay 결제와는 결제 방식이 다르다.
	// 따라서 encodeSecretKey 에 widgetSecretKey 를 사용하는 것이다.
	@PostMapping("/widget")
	public ResponseEntity<?> confirmWidget(@RequestBody Map<String, String> requestBody) {
		return confirmPayment(requestBody, encodeSecretKey(widgetSecretKey));
	}
	
	// payment 라는 주소로 결제 정보가 들어오면, 결제 확인 창구로 결제 정보와 결제 주체(사용자) 의 비밀번호를 넘겨주는 것이다.
	@PostMapping("/payment")
	public ResponseEntity<?> confirmPayment(@RequestBody Map<String, String> requestBody) {
		return confirmPayment(requestBody, encodeSecretKey(apiSecretKey));
	}
	
	// brandpay 라는 주소로 결제 정보가 들어오면, 결제 확인 창구로 결제 정보와 결제 주체(사용자) 의 비밀번호를 넘겨주는 것이다.
	// return 하는 곳이 다르다.
	@PostMapping("/brandpay")
	public ResponseEntity<?> confirmBrandpay(@RequestBody Map<String, String> requestBody) {
		return confirmBrandpayPayment(requestBody, encodeSecretKey(apiSecretKey));
	}
	
	private ResponseEntity<?> confirmPayment(Map<String, String> requestBody, String encodeKey) {
		String url = "https://api.tosspayments.com/v1/payments/confirm";
		HttpHeaders headers = new HttpHeaders();
		// Authorization: encryptedApiSecretKey
		headers.set("Authorization", encodeKey); // encryptedApiSecretKey 의 명칭을 encodeKey 라고 작성해 놓았다.
		
		// "Content-Type": "application/json"
		headers.set("Content-Type", "application/json"); // encryptedApiSecretKey 의 명칭을 encodeKey 라고 작성해 놓았다.
		
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
		
		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
		
		return new ResponseEntity<>(response.getBody(), response.getStatusCode());
	}
	
	private ResponseEntity<?> confirmBrandpayPayment(Map<String, String> requestBody, String encodeKey) {
		// fetch("https://api.tosspayments.com/v1/brandpay/payments/confirm", {
		String url = "https://api.tosspayments.com/v1/brandpay/payments/confirm";
		HttpHeaders headers = new HttpHeaders();
		// Authorization: encryptedApiSecretKey
		headers.set("Authorization", encodeKey); // encryptedApiSecretKey 의 명칭을 encodeKey 라고 작성해 놓았다.
		
		// "Content-Type": "application/json"
		headers.set("Content-Type", "application/json"); // encryptedApiSecretKey 의 명칭을 encodeKey 라고 작성해 놓았다.
		
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
		
		// 성공했을 때와 실패했을 때 나누기		
		try {
			ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
			return new ResponseEntity<>(response.getBody(), response.getStatusCode());
		} catch (Exception e) {
			// 사용자(client) 에게 보내는 응답 실패 메시지, 잘못된 요청으로 정상적으로 실행되지 않았다는 상태 코드를 보낸 것
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Entity
	 * 
	 * HttpEntity: HTTP 요청 또는 응답의 본문(body) 과 헤더(headers) 를 포함하는 객체이다.
	 * 				   HTTP 요청을 보낼 때, 본문과 헤더를 설정하고 싶을 때 사용한다.
	 * 		본문(body): 실제 전송될 데이터(id, pw, 작성 글 등)
	 * 		헤더(headers): HTTP 헤더 정보를 포함한다.(전송될 데이터의 종류: 비디오/이미지/텍스트 등, 데이터 전송 주체: 누가 보내는지)
	 * 					HttpEntity<문자열이면 문자열, 숫자면 숫자, 모르겠으면 비워두기> he = new HttpEntity<공란이어도 상관없다.>("요청 본문", headers);
	 * 
	 * 
	 * 
	 * ResponseEntity(응답): 사용자(client) 가 요청한 응답을, 개발자(server) 가 사용자에게 다시 전달할 때 사용한다.
	 * 					    HTTP 기능에 응답에 대한 기능을 추가로 더해 설정한 Entity
	 * 						HttpEntity 를 상속받아, Http 응답에 대한 추가적인 정보를 제공한다.
	 * 						상태 코드를 포함하고 있어서, 사용자(client) 에게 응답을 보낼 때 사용한다. 
	 * 
	 * ResponseEntity<문자열이면 String, 숫자 값이면 Integer, 여러 값이면 ?, 모르겠으면 공란으로 비워두기> = new ResponseEntity<>("응답 본문", headers);
	 * 
	 * 
	 * 
	 * RequestEntity(요청): HTTP 를 상속받아, HTTP 기능에 요청에 대한 기능을 추가로 더해 설정한 Entity
	 * 					   HttpEntity 를 상속받아, Http 요청에 대한 추가적인 정보를 제공한다.
	 * 					   URI 와 HTTP 메서드(GET, POST, PUT, DELETE) 를 포함하고 있어서, 개발자(server) 에게 요청을 보낼 때 사용한다.
	 * 
	 * RequestEntity<문자열이면 String, 숫자 값이면 Integer, 여러 값이면 ?, 모르겠으면 공란으로 비워두기> = new RequestEntity<>("요청 본문", headers);
	 * RequestEntity<String> req = new RequestEntity<>("요청 본문", headers, HttpMethod.POST, url);
	 * 
	 * 
	 * 
	 * 차이점 요약
	 * 	   클래스				상속 관계			 주요 사용 목적					추가 정보
	 * HttpEntity	   	   기본 클래스		HTTP 요청/응답 본문과 헤더 포함		  상태 코드 없음(성공 여부)
	 * ResponseEntity	HttpEntity 상속	    HTTP 응답 반환				  상태 코드 포함(성공 여부)
	 * RequestEntity	HttpEntity 상속	    HTTP 요청 전송				  URI 와 HTTP 메서드 포함
	 * 
	 * 
	 * 
	 * HTTP(Hyper Text Transfer Protocol): 웹에서 데이터를 전송하기 위한 전송 수단이다.
	 * 
	 * URI(Uniform Resource Identifier): 자원의 식별값과 주소값, 이름값이 포함되어 있으며, URL 과 URN 을 포함하는 포괄적인 개념이다.
	 * URL: URI 의 한 종류로, 자원의 주소값이다.
	 * URN: 자원의 고유한 이름이다.
	 * **/
}
