package com.kh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * BCryptPasswordEncoder ◀ 이 이름으로 객체를 생성하면 안 된다.
 * Blowfish ◀ 암호 알고리즘을 기반으로 한다.
 * Crypt ◀ 암호화(Encrypt 의 줄임말)
 * Password ◀ 비밀번호
 * Encoder ◀ 데이터를 특정 방식으로 변환하는 역할을 한다.
 * 
 * 시큐리티를 사용하기 위해서는, build.gradle 설정에 시큐리티를 implementation 으로 보안을 추가해주어야 한다.
 * 
 * SecurityConfig
 * Security ◀ 보안
 * Config ◀ 설정
 * **/
@Configuration // Config 관련 class 에는 무조건 이 애너테이션이 있어야 한다.
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// http 메서드(GET, POST, PUT, DELETE) 모든 동작 허용 ◀ 인증이나 권한에 대한 검사를 하지 않고, 모든 요청에 대한 접근을 허용해주는 것이다. 
        http.authorizeHttpRequests(authorize -> authorize
        		.anyRequest().permitAll()) // 비밀번호 없이 9012 port 에 들어갈 수 있도록 연결을 허용하는 코드
        .csrf(csrf -> csrf.disable()); // 3000 port 또는 외부에서 오는 보호는 비활성화하는 코드이다. 
        // build ◀ 위에서 작성된 http 에 대한 상세 설명을 바탕으로, http 관련 설정을 쌓아올린다는 느낌이다.
        return http.build();
    }

	// @Bean 비밀번호 관련 객체 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}