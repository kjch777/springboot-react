package com.kh.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity // MySQL 에 테이블이 존재하지 않는다면, 테이블을 생성해주는 애너테이션
@Getter // import 가 제대로 되지 않는다면, build.gradle 의 dependencies 가 제대로 작성됐는지 확인할 것
@Setter // 이후, gradle refresh 진행
public class Chicken {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 대용
	private int id;
	
	private String chickenName;
	private String description;
	
	private double price; // 소수점 고려
}
