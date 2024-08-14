package com.kh.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kh.dto.BCUser;

@Mapper
public interface PasswordMapper {

	void insertUser(BCUser bcUser);
}
