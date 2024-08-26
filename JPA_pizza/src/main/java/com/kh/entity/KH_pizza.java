package com.kh.entity;

import jakarta.persistence.Entity; // lombok 에도 Entity 가 존재하기 때문에, jakarta 로 표기 명시를 확실히 해주어야 한다.
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * MODEL: DTO, Entity, VO
 * 
 * DB에 이미 존재하는 테이블과 연결하는 것 ◀ DTO
 * DB에 해당하는 테이블이 존재하지 않아서 생성해주어야 할 때, DB와 JAVA에서 객체로 사용하는 것 ◀ Entity
 * DB와 관계없음 ◀ VO 
 * **/
@Entity // 만약, DB에는 pizzas 로 테이블을 저장하고 싶다면, @Table("name = pizzas") 형식으로 작성해주면 된다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KH_pizza {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String pizzaName;
	private String description;
	private double price;
}
