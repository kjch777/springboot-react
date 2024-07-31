package com.kh.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
// import jakarta.servlet.http.HttpSession; ◀ javax 로 import 하게 되면 오류가 발생한다.
// javax 옛날 버전 ▶ jakarta 최신 버전
/**
 * 24-07-30 NaverLogin 을 한 뒤, 로그인 한 내용을 React 에서 볼 수 있도록,
 * NaverLoginController.java 파일 수정
 * NaverLoginController.java 주소(api url) 충돌을 방지하기 위해
 * @RequestMapping("/api") 을 제거한다.
 * **/
@RestController
@RequestMapping("/naver") // NaverRegist 와의 주소 충돌을 방지하기 위해 임의 작성
public class OAuthController {

	/**
	 * 환경설정 @Value = application.properties 에 작성한
	 * key 명칭을 가져오고, key 에 담긴 value 를 가져오는 애너테이션이다.
	 * **/
	@Value("${naver.client-id}")
	private String clientId; 
	
	@Value("${naver.client-secret}")
	private String clientSecret; 
	
	@Value("${naver.state}")
	private String state; 
	
	@Value("${naver.redirect-uri}")
	private String redirectUri; 
	
	@GetMapping("/naverLogin") 
	public String naverLogin() {
		String api_url = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri + "&state=" + state;
		return "<a href='"+ api_url + "'><img height='50' src='http://static.nid.naver.com/oauth/small_g_in.PNG'/></a>";
	}
	
	@GetMapping("/callback")
	public String callback(@RequestParam String code, @RequestParam String state, HttpSession session) {
		String api_url = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id="
			     + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUri + "&code=" + code + "&state=" + state;
		
		RestTemplate restTemplate = new RestTemplate();
		
		// ▼ 여기서부터 다르다.
		// 앞의 값은 key 명칭이기 때문에 String, 
		// key 명칭에 담긴 value 는 String 이라는 확신이 없으므로, Object 이다.
		Map<String, Object> resResult = restTemplate.getForObject(api_url, Map.class);
		System.out.println("Token Response: " + resResult);
		
		// token 인증받은 값을 가져올 것
		// Bearer access_token 사용
		// ▼ 가져온 token 데이터를 문자열로 변환하여 text 처럼 사용하겠다.
		String accessToken = (String) resResult.get("access_token");
		// 네이버 개발자 문서를 보면, access_token 으로 로그인 허용된 값을 가져가라고 작성되어 있다.
		
		String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
		HttpHeaders header = new HttpHeaders(); // import org.springframework.http.HttpHeaders;
		header.set("Authorization", "Bearer " + accessToken); // 네이버 개발자 센터에서 제공한 작성 양식
		
		HttpEntity<String> entity = new HttpEntity<>("", header);
		// HttpEntity 안에는 응답과 요청이 모두 있지만, 상세한 기능은 없다.
		// ResponseEntity 와 RequestEntity 는 각각 상세한 기능을 보유하고 있다.
		
		ResponseEntity<Map> userInfoRes = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
		
		Map<String, Object> userInfo = userInfoRes.getBody();
		
		session.setAttribute("userInfo", userInfo); // session 에 로그인 정보를 담아주겠다.
		
		/**
		 * HttpHeaders 에 있는 인증에 대한 값을, Bearer 를 사용하여 가져오기
		 * 인증을 위해 서버에서 제공되는 보안 token 으로, 주로 client 가 인증을 받고 난 뒤, API 요청을 할 때 사용된다.
		 * 
		 * 예를 들어, 네이버에 로그인을 하고 나면 네이버 사용자에게 로그인되었다는 token 을 발급하는 것이다.
		 * 추후, 네이버에 로그인 된 기록을 가지고 어떠한 요청을 하면,
		 * 요청을 할 때 header 에 "Authorization:Bearer{ }" 를 작성하고 요청을 해야한다.
		 * 
		 * Bearer: 운송자/전달자/소지자
		 * Authorization: 권한 부여
		 * Authorization:Bearer{ }
		 * 권한 부여: 운송자{"권한을 가지고 있는 번호"}
		 * **/ 
		
		return "redirect:";
	}
	
	// 가져온 네이버 회원정보를 전달할 GetMapping
	@GetMapping("/userInfo")
	public Map<String, Object> userInfo(HttpSession hs) {
		//                            HttpSession 을 Map 으로 형변환
		Map<String, Object> userInfo = (Map<String, Object>) hs.getAttribute("userInfo");
		return userInfo;
	}
}
