package com.kh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // front 포트로 가져오는 뒤 API URL 주소 모두 허용
				.allowedOrigins("http://localhost:3000") // 주소와 포트 허용
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // DB와 주고받고 삭제수정하고 기타등등 모두허용
		        .allowCredentials(true); // 쿠키 또는 세션과 같은 자격 허용 ◀ true 또는 "*" 사용 가능하다.
	}
}
