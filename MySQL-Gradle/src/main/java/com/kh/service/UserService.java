package com.kh.service;

import java.util.List;

import com.kh.dto.User;

// service list 를 작성하는 공간으로, 여기에는 목록만 작성하고, impl override 해서 각 환경에 맞게 재사용 할 것이다.
public interface UserService {

	List<User> findAll();
	void insertUser(User user); 
}
