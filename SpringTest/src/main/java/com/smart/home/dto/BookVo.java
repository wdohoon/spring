package com.smart.home.dto;

import lombok.Data;

@Data
public class BookVo {
	private int num;
	private String title;
	private String author;
	private String company;
	private String isbn;
	private int count;
}
