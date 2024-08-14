package com.kh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.dto.BCUser;
import com.kh.mapper.PasswordMapper;
import com.kh.repository.BCUserRepository;

@Service
public class BCUserService {

	@Autowired
	private BCUserRepository bcUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 비밀번호를 인코드하여 저장하기
	public void saveUser(BCUser bcUser) {
		
		// 한 번 암호화 처리 된 암호를 가져오는 것이다.
		bcUser.setPassword(passwordEncoder.encode(bcUser.getPassword()));
		
		// JPA Repository 안에 save 가 이미 존재하기 때문에, BCUserRepository 안에 미리 작성해둘 필요가 없다.
		bcUserRepository.save(bcUser);
	}
	
	@Autowired
	PasswordMapper passwordMapper;
	
	public void insertUser(BCUser bcUser) {
		passwordMapper.insertUser(bcUser);
	}
}
