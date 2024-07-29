package com.kh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.dto.User;

// 여기에는 mybatis xml 파일에서 id 값으로 작성한 SQL 기능 목록을 주로 작성한다.
// 기능에 대한 목록만 보기 때문에 interface 이다.
@Mapper
public interface UserMapper {

	List<User> findAll();
	void insertUser(User user);
	void deleteUser(int id);
	void updateUser(User user);
}
