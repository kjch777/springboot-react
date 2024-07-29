package com.kh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dto.User;
import com.kh.mapper.UserMapper;

/**
 * implements 로 상속받는 상위 인터페이스에는, 기능에 대한 목록만이 작성되어 있을 뿐, 상세한 기능에 대해 작성된 것은 아니다.
 * 상속을 받은 클래스는, 상위 인터페이스에 작성된 각각의 목록마다 기능을 설정해주어야 하기 때문에,
 * 설정되지 않은 목록이 있을 경우, 오류가 발생한다.
 * **/
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
	
	@Override
	public void deleteUser(int id) {
		userMapper.deleteUser(id);
	}
	
	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}
}
