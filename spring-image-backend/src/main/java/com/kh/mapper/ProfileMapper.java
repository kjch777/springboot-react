package com.kh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.dto.Profile;

@Mapper
public interface ProfileMapper {

	List<Profile> getProfile();
	void postProfile(Profile profile);
}
