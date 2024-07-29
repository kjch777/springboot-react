package com.kh.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 필수 생성자
public class User {

	private int id;
	private String name;
	private String email;
}
