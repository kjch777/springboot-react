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

import com.kh.dto.Profile;
import com.kh.mapper.ProfileMapper;

// 서비스의 상세 기능을 작성하는 공간
@Service
public class ProfileServiceImpl implements ProfileService {

	@Value("${file.upload-dir}")
	private String profileDir;

	@Autowired
	private ProfileMapper profileMapper;
	/**
	 * . 과 Ctrl + Space 를 적극 활용하자.
	 * **/
	@Override
	public List<Profile> getProfile() {
		return profileMapper.getProfile();
	}
	
	@Override
	public void postProfile(Profile profile) {
		profileMapper.postProfile(profile);
	}
	
	@Override
	public void uploadProfile(MultipartFile[] files, String username, String profileImageUrl) {
		// 폴더가 존재하는지 확인 후, 존재하지 않는다면 생성해주기
		// 폴더 또한 하나의 파일이므로, 파일로 폴더 확인을 진행한다.
		// 글자 이외에는 모두 파일이라고 생각하면 쉽다.
		File uploadDirFile = new File(profileDir);
		
		if(!uploadDirFile.exists()) {
			System.out.println("폴더가 존재하지 않으므로, 생성한다.");
			if(!uploadDirFile.mkdirs()) {
				System.out.println("Folder creation completed!");
				// throw new Exception("폴더 생성 실패");
			}
		} 	

		// 아직 프로필을 업데이트 하기 위해 받은 이미지가 없기 때문에, 맨 처음 이미지 이름은 null 로 지정한다.
		List<String> fileNames = null;
		
		fileNames = List.of(files).stream().map(file -> {
			
			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			File df = new File(profileDir + File.separator + fileName);
			
			try {
				file.transferTo(df);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			return fileName;
		
		}).collect(Collectors.toList());
		
		Profile profile = new Profile();
		profile.setUsername(username);
		profile.setProfileImageUrl(String.join(",", fileNames));
		postProfile(profile); // set 으로 추가된 값을 DB 에 넣어주기
	}
}