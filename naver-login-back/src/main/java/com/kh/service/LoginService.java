package com.kh.service;

import com.kh.dto.NaverUser;

public interface LoginService {

	NaverUser login(String id, String password);
}
