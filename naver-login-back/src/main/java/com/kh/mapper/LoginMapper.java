package com.kh.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kh.dto.NaverUser;

@Mapper
public interface LoginMapper {

	NaverUser login(String id, String password);
}
