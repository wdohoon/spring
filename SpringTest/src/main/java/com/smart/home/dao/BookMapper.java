package com.smart.home.dao;

import java.util.List;

import com.smart.home.dto.BookVo;

public interface BookMapper {
	public List<BookVo> bookList();
	public int bookInsert(BookVo Vo);
}
