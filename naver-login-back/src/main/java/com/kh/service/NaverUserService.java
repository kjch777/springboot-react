package com.kh.service;

import java.util.List;

import com.kh.dto.NaverUser;

// service list 를 작성하는 공간으로, 여기에는 목록만 작성하고, impl override 해서 각 환경에 맞게 재사용 할 것이다.
// 여기에서는 기능을 작성하기 전에, 상세하게 작성할 기능 목록을 구성한다.
public interface NaverUserService {

	List<NaverUser> findAll();
	
	void insertNaverUser(NaverUser naverUser);
}
