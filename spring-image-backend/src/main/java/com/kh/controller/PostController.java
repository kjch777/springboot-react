package com.kh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.dto.Post;
import com.kh.service.PostService;

@RestController
public class PostController {

	@Autowired
	private PostService postService;
	
	// 이미지를 저장하기 위한 PostMapping
	@PostMapping("/gallery/upload")
	public ResponseEntity<String> uploadImages(@RequestParam("files") MultipartFile[] files,
											   @RequestParam("title") String title,
											   @RequestParam("content") String content) { // ResponseEntity ◀ 데이터가 무사히 전달되고 있는지 확인해주는 용도
		
		postService.uploadImages(files, title, content);
		return ResponseEntity.ok("DB 에 이미지 업로드 성공");
	}
	
	@GetMapping("/posts") // DB 에 저장된 게시물 제목, 내용, 이미지 가져오기
	public ResponseEntity<List<Post>> findAll() { // <이것은 객체를 구분하는 구분 괄호이다.>
		// postService.findAll(); ◀ 데이터가 여럿이라 List 로 가져와야 하는데, List 로 전달해주지 않으면 React 에서 map ~ 오류가 발생한다.
		
		// List<Post> posts = postService.findAll();
		// return ResponseEntity.ok(posts);
		// 또는 ▼
		return ResponseEntity.ok(postService.findAll());
	}
}
