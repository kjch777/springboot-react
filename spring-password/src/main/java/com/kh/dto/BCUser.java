package com.kh.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity // MySQL 매핑
public class BCUser {

	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 id 값이 올라가는 시퀀스(NextVAL) 과 같다.
	private int id;
	private String username;
	private String email;
	private String password;
}
