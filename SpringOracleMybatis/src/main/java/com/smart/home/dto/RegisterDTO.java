package com.smart.home.dto;

public class RegisterDTO {
	private String userid;
	private String userpwd;
	private String username;
	
	private String tel1;
	private String tel2;
	private String tel3;
	private String tel;
	
	private String email;
	
	@Override
	public String toString() {
		return "RegisterDto [userid=" + userid + ", userpwd=" + userpwd + ", username=" + username + ", tel1=" + tel1
				+ ", tel2=" + tel2 + ", tel3=" + tel3 + ", tel=" + tel + ", email=" + email + "]";
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getTel3() {
		return tel3;
	}

	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}

	public String getTel() {
		return tel1+"-"+tel2 +"-"+tel3;
	}

	public void setTel(String tel) {
		this.tel = tel;
		// - 기준으로 연락처 자른다.
		String t[] = tel.split("-");
		tel1 = t[0];
		tel2 = t[1];
		tel3 = t[2];
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
