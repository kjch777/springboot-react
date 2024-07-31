package com.kh.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 외부 도메인에서 요청을 주고 받을 수 있도록 허용하는 것이다.
 * 설정을 통해 특정 도메인에서 오는 요청을 허용할 수 있고,
 * 허용할 HTTP 메서드(GET, POST, PUT, DELETE) 를 지정할 수 있다.
 * **/
@Configuration // 개발에 관련된 환경 설정을 하겠다.
public class WebConfig implements WebMvcConfigurer {
	
	
	@Override // WebMvcConfigurer mapping 재설정
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // ◀ ▼ http://localhost:3000/ 뒤에 오는 모든 주소 값 허용
		.allowedOrigins("http://localhost:3000")  
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 모든 기능 허용
		.allowCredentials(true); // 쿠키나 세션과 같은 자격을 허용한다는 의미의 코드이다.
	}
}
