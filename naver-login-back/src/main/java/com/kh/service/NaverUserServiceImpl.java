package com.kh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.dto.NaverUser;
import com.kh.mapper.NaverUserMapper;

/**
 * implements 로 상속받는 상위 인터페이스에는, 기능에 대한 목록만이 작성되어 있을 뿐, 상세한 기능에 대해 작성된 것은 아니다.
 * 상속을 받은 클래스는, 상위 인터페이스에 작성된 각각의 목록마다 기능을 설정해주어야 하기 때문에,
 * 설정되지 않은 목록이 있을 경우, 오류가 발생한다.
 * **/
@Service // Service 기능을 상세하게 작성해주는 공간
public class NaverUserServiceImpl implements NaverUserService {

	@Autowired
	NaverUserMapper naverUserMapper;
	
	@Override
	public List<NaverUser> findAll() {
		// 관리자로 로그인 했을 때 사용할 기능을 작성해줄 수 있다.
		return naverUserMapper.findAll();
	}
	
	@Override
	public void insertNaverUser(NaverUser naverUser) {
		naverUserMapper.insertNaverUser(naverUser);
	}
}
