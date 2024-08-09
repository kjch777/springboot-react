package com.kh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.dto.Profile;
import com.kh.service.ProfileService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService; // ProfileService 말고 ProfileServiceImpl 을 가져와도 상관없다.
	
	/**
	 * ▲ 와 ▼ 는 같은 기능을 한다.(@Autowired 유무 차이이다.)
	 * public ProfileController(ProfileService profileService) {
	 * 		this.profileService = profileService;
	 * }
	 * **/
	
	@GetMapping("/viewing")
	public ResponseEntity<List<Profile>> getProfile() {
		return ResponseEntity.ok(profileService.getProfile());
	}
	
	/**
	 * React 에서 값을 가져올 때, @RequestParam 안에 값을 가져올 변수명을 작성하지 않으면, Parameter Type Error 가 발생한다.
	 * @RequestParam("React 에서 값을 가져올 변수명")
	 * **/
	@PostMapping("/upload")
	public ResponseEntity<String> postProfile(@RequestParam("files") MultipartFile[] files,
											  @RequestParam("username") String username) {
		
		profileService.uploadProfile(files, username);
		
		return ResponseEntity.ok("이미지 업로드 컴플릿");
	}
	
}
