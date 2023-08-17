package com.smart.home;

public class TestDto {
	private int num;
	private String username;
	private String tel;
	
	@Override
	public String toString() {
		return "TestDto [num=" + num + ", username=" + username + ", tel=" + tel + "]";
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	

}
