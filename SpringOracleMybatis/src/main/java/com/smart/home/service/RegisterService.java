package com.smart.home.service;

import com.smart.home.dto.RegisterDTO;

public interface RegisterService {
	public int registerInsert(RegisterDTO dto);
	public RegisterDTO loginOk(String userid, String userpwd);
	public RegisterDTO idSearch(RegisterDTO dto);
	
}
