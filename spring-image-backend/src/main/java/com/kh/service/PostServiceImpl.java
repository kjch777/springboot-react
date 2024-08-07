package com.kh.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.dto.Post;
import com.kh.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostMapper postMapper;

	// import org.springframework.beans.factory.annotation.Value;
	@Value("${file.upload-dir}") // application.properties 에서 설정명을 가져와,
	private String uploadDir; // private String uploadDir = "C:/Users/user1/Desktop/saveImg";
	// file.upload-dir=C:/Users/user1/Desktop/saveImg 경로를 넣어서 사용한다.
	
	@Override
	public List<Post> findAll() {
		return postMapper.findAll();
	}
	
	@Override
	public void insertPost(Post post) {
		postMapper.insertPost(post);
	}
	
	@Override
	public void uploadImages(MultipartFile[] files, String title, String content) {
		// 바탕화면에 이미지 저장하고 불러올 폴더가 존재하는지 확인 후, 없으면 폴더 생성
		File uploadDirFile = new File(uploadDir);
		// 만약 폴더가 존재할 경우
		// uploadDirFile.exists() ◀ 폴더가 존재한다.
		// !uploadDirFile.exists() ◀ 폴더가 존재하지 않는다.
		if(!uploadDirFile.exists()) {
			System.out.println("폴더 생성 진행");
			if(!uploadDirFile.mkdirs()) { // mkdir: 폴더 1개 생성, mkdirs: 하위 폴더 모두 생성
				throw new RuntimeException("폴더 생성 실패");
			}
		}
		// UUID 이미지 이름을 저장할 때 중복없이 저장할 수 있도록 설정하기
		List<String> fileNames = null; // 파일명이 1개일 수도 있고, 여러개의 이름이 들어올 수도 있기 때문에
		// fileNames = 파일 이름들(List 로 글자 목록을 작성)
		try {
			// MultipartFile[] files ◀ array 배열로 파일들 담기
			// List.of(files) ◀ 파일들 배열을 List(목록) 로 변환하는 것
			// .stream() ◀ List 또는 배열과 같은 데이터를 하나씩 처리하는 기능
			// .collect(Collectors.toList()) ◀ stream 으로 가져온 이미지 데이터를 List 로 정렬
			
			// 이미지들 이미지를 하나씩 담아오기 map -> 이미지를 하나씩 가져올 때 stream 을 이용하여 가져오기
			// 스트림을 이용해서 가져온 이미지를 collect 를 이용하여 List 로 모음
			// 한번 더 List 로 목록 변환(안전성을 위함)
			fileNames = List.of(files).stream().map(file -> {

				// 파일을 폴더에 저장(폴더에 파일을 저장할 때, 이미지에 랜덤하게 이름을 부여하여 저장하기)
				// UUID(파일 이름이 겹치지 않도록 랜덤으로 이름 생성) 를 이용하여 저장하기
				String fileName1 = UUID.randomUUID().toString();
				
				// 랜덤하게 부여된 이름 뒤에 원본 이름을 붙이고 싶다면,
				// + file.getOriginalFilename()
				String fileName2 = UUID.randomUUID().toString() + file.getOriginalFilename();
				
				// 랜덤하게 부여된 이름 뒤에 _원본이름 형식으로, 랜던 이름과 원본 이름을 구분짓고 싶을 때
				String fileName3 = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
				
				// 폴더 안에 이미지들 저장하기 최종 완성
				// File.separator(경로 구분) ◀ window(\, /) 와 mac(/) 모두 알아서 경로를 잡을 수 있도록 설정해주는 것
				File df = new File(uploadDir + File.separator + fileName3);
				
				try {
					file.transferTo(df);
				} catch (Exception e) {
					throw new RuntimeException("파일 업로드 실패", e);
				} 
				
				return fileName3;
			}).collect(Collectors.toList());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 이미지의 이름 설정과 이미지의 경로를 생성한 것을 바탕으로 DB 에 넣어주기
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setImageUrl(String.join(",", fileNames)); // 여러개의 이미지를 각각 , 로 구분짓기
		insertPost(post);
	}
	
}
