package com.kh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dto.User;
import com.kh.mapper.UserMapper;

@Service // Service 기능을 상세하게 작성해주는 공간
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public List<User> findAll() {
		// 관리자로 로그인 했을 때 사용할 기능을 작성해줄 수 있다.
		return userMapper.findAll();
	}
	
	@Override
	public void insertUser(User user) {
		userMapper.insertUser(user);
		// void 와 return 은 공존할 수 없다.
	}
}
