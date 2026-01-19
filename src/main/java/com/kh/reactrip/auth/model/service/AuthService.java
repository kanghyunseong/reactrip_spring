package com.kh.reactrip.auth.model.service;

import java.util.Map;

import com.kh.reactrip.auth.model.dto.MemberLoginDTO;


public interface AuthService {
	Map<String, String> login(MemberLoginDTO member);
}
