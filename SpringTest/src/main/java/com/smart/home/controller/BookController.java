package com.smart.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.home.dto.BookVo;

@Controller
public class BookController {
	@Autowired
	BookVo mapper;
	
	@RequestMapping("/bookList.do")
	public void bookList(int num) {
		bookList list = mapper.bookList(num);
		mapper.addAttribute("list", list);
	}
	
	@RequestMapping("/bookInsert.do")
	public String bookInsert(bookList) {
		mapper.bookInsert(vo);
		return String;
	}
}
