package com.kh.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
/**
 * 24-07-29 Spring framework - React 연결을 위한 Controller
 * OAuthController 와 api url 주소가 동일해서 발생하는 충돌을 막기 위해,
 * // @RequestMapping("/api") ◀ 여기서 주석을 지워,
 * 모든 url 앞에 api 가 붙도록 설정해주기
 * **/
@RestController
@RequestMapping("/api")
public class NaverLoginController {

	/**
	 * 환경설정 @Value = application.properties 에 작성한
	 * key 명칭을 가져오고, key 에 담긴 value 를 가져오는 애너테이션이다.
	 * **/
	// application.properties naver.client-id = Qe9stikwcjgjw9xn3CV1
	@Value("${naver.client-id}")
	private String clientId; // client-id = Qe9stikwcjgjw9xn3CV1
	
	// application.properties naver.client-secret = gUGy3y0U1U
	@Value("${naver.client-secret}")
	private String clientSecret; // client-secret = gUGy3y0U1U
	
	// application.properties naver.state = RANDOM_STATE
	@Value("${naver.state}")
	private String state; // state = RANDOM_STATE
	
	// application.properties naver.redirect-uri=http://localhost:9010/naverLogin
	@Value("${naver.redirect-uri}")
	private String redirectUri; // redirect-uri=http://localhost:9010/naverLogin
	
	/*
	 * app.get('/naverLogin', function (req, res) {
  	   api_url = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=' + client_id + '&redirect_uri=' + redirectURI + '&state=' + state;
       res.writeHead(200, {'Content-Type': 'text/html;charset=utf-8'});
       res.end("<a href='"+ api_url + "'><img height='50' src='http://static.nid.naver.com/oauth/small_g_in.PNG'/></a>");
 	   });
	 * */
	@GetMapping("/naverLogin") // http://localhost:9010/api/naverLogin
	public String naverLogin() {
		String api_url = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri + "&state=" + state;
		return "<a href='"+ api_url + "'><img height='50' src='http://static.nid.naver.com/oauth/small_g_in.PNG'/></a>";
	}
	
	/*
	 * url 에 { } = 변수명 표시가 없으면, @RequestParam 또는 @RequestBody 이다.
	 * url 에 { } = 변수명 표시가 있으면, @PathVariable 이다. { } 안에 있는 변수명에 값을 집어넣는다.
	 * */
	@GetMapping("/callback")
	public String callback(@RequestParam String code, @RequestParam String state) {
		// 네이버에서 로그인을 성공했을 때 받는 값이다.
		// 1. clientId: 어디에 들어왔는지
		// 2. clientSecret: 들어오기 위한 비밀번호
		// 3. redirectUri: 정상적으로 들어왔다면 내보내는 위치로 전달
		// 4. code: 네이버로부터 정상적으로 들어왔다는 인증 코드 받기
		// 5. state: CSRF 공격을 방지하기 위해 사용한다.
		String api_url = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id="
			     + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri + "&code=" + code + "&state=" + state;
		
		// RestTemplate == HTTP 메서드(GET/POST/PUT/DELETE 등) 를 통해
		// 데이터를 JSON 형식으로 처리하는 것이다.
		RestTemplate restTemplate = new RestTemplate();
		
		// api_url 주소로 응답받은 결과를, String(문자열) 으로 가져와서 사용하겠다는 뜻이다.
		String resResult = restTemplate.getForObject(api_url, String.class);
		return resResult;
	}
}
