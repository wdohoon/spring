package com.smart.home.dao;

import com.smart.home.dto.RegisterDTO;

public interface RegisterDAO {
	public int registerInsert(RegisterDTO dto);
	// ·Î±×ÀÎ		userid, userpwd	->	userid, username
	public RegisterDTO loginOk(String userid, String userpwd);
	public RegisterDTO idSearch(RegisterDTO dto);
}
