package com.kh.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	private int id;
	private String title;
	private String content;
	private String imageUrl;
	// id 와 created_at 은 MySQL 에서 자동으로 숫자와 날짜 생성을 해주기 때문에,
	// mapper.xml 에 작성해주지 않는다.
	private String createdAt; // 게시판에 작성한 이미지와 텍스트가 MySQL 에 들어온 시간
}
