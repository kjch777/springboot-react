package com.kh.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// React Port: 3000, SpringBoot BackEnd Port 가 서로 연결되도록 설정해주는 공간이다.
// WebSocket 프론트엔드와 백엔드가 서로 상호작용을 주기적으로 진행할 때, 
// 더 안전하게 연결을 계속 진행하겠다는 설정을 해주는 공간이다. 

@Configuration // 설정
public class WebConfig implements WebMvcConfigurer {

	// 이미지 폴더 경로를 React 가 가져갈 수 있도록 허용해주기
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry r) {
		r.addResourceHandler("/images/**")
		 .addResourceLocations("C:/Users/user1/Desktop/saveImg/"); // 바탕화면에 지정한 이미지 경로 넣어주기
	}
	
	@Override
	public void addCorsMappings(CorsRegistry cs) {
		cs.addMapping("/**")
		  .allowedOrigins("http://localhost:3000")
		  // .allowedOrigins("*") ◀ 모든 주소 허용(기능이 제대로 동작하지 않을 가능성이 있다.)
		  .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		  .allowedHeaders("*");
	}
	/**
	 * .addMapping("/**") ▼ 뒤에 오는 모든 URL api 주소를 모두 허용
	 * .allowedOrigins("http://localhost:3000") 이 주소에서
	 * ▲ http://localhost:3000/** 들어오는 모든 요청 허용
	 * 
	 * .allowedMethods("보기", "넣기", "수정하기", "삭제하기", "기타 등등")
	 * .allowedHeaders("*"); <html> <head> 정보에 들어갈 모든 요청 허용
	 * **/
}
