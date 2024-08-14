package com.kh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// React Port 3000 에서의 접속 허용
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 3000 뒤에 붙는 URL 모두 허용
		.allowedOrigins("http://localhost:3000") // 3000 허용
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP 메서드 모두 허용
		.allowCredentials(true); // 쿠키, 세션과 같은 자격 허용
	}
}
