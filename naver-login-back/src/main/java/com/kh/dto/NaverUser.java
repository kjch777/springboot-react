package com.kh.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 필수 생성자
public class NaverUser {

	// id ~ profileImage ◀ 네이버에 저장된 값을 가져와 DB 에 저장할 것
	// password ◀ client 가 직접 작성한 비밀번호를 가져와 DB 에 저장할 것
	private String id;
	private String email;
	private String nickname;
	private String name;
	private String gender;
	private String profileImage;
	
	private String password;
}
