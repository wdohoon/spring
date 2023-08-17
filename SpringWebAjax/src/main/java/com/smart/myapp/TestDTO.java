package com.smart.myapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
	private int num;
	private String username;
	private String tel;
	private String addr;
}
