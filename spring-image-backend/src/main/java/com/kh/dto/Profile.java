package com.kh.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString // DB 에서 값이 제대로 넘어왔는지 Check 용
public class Profile {

	private int userId;
	private String username;
	private String profileImageUrl;
	// id 와 created_at 은 MySQL 에서 자동으로 숫자와 날짜 생성을 해주기 때문에,
	// mapper.xml 에 작성해주지 않는다.
	private LocalDateTime createdAt;
	// Localhost: 현재 내 주소
	// LocalDateTime: 현재 날짜와 시간
}
