package com.smart.myapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	// @RequesstMapping, @GetMapping, @PostMapping
	@RequestMapping("/ajaxStart")
	public String ajaxStart() {
		return "ajax/ajaxView";
	}
	// 반환이 String일 경우만 인코딩을 기술하여야 한다.
	@RequestMapping(value="/ajaxString", method=RequestMethod.GET, produces="application/text;charset=UTF-8")
	@ResponseBody
	public String ajaxString(String name, int age) {
		System.out.println(name+", "+age);
		
		return "ajax로 서버에서 받은 값은 ("+name+","+(age+10)+")";
	}
	@GetMapping("/ajaxObject")
	@ResponseBody
	public TestDTO ajaxObject(TestDTO dto) {//이름,주소
		System.out.println(dto.toString());
		
		dto.setNum(1234);
		dto.setTel("010-5555-6666");
		
		return dto;
	}
	@GetMapping("/ajaxList")
	@ResponseBody
	public List<TestDTO> ajaxList(String productName, int price){
		System.out.println("상품명:"+ productName+", 가격:"+price);
		
		List<TestDTO> list = new ArrayList<TestDTO>();
		list.add(new TestDTO(100,"홍길동","010-1234-5678","서초구"));
		list.add(new TestDTO(100,"홍길동2","010-1234-1234","서초구2"));
		list.add(new TestDTO(100,"홍길동3","010-1234-2345","서초구3"));
		list.add(new TestDTO(100,"홍길동4","010-1234-3456","서초구4"));
		
		return list;
	}
	// 게시판목록, 총레코드수, 페이지수
	@GetMapping("/ajaxMap")
	@ResponseBody
	public HashMap<String, Object> ajaxMap() {
		// 게시판목록
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		boardList.add(new BoardDTO(95,"공부중"));
		boardList.add(new BoardDTO(93,"재밌다"));
		boardList.add(new BoardDTO(89,"재밌냐"));
		boardList.add(new BoardDTO(71,"재밌어"));
		// 총레코드수
		int totalRecord = 150;
		// 총페이지수
		int page = 6;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", boardList);
		map.put("total", totalRecord);
		map.put("page", page);
		
		return map;
	}
	/*
	 * var data = {
	 * 	name: 'gildong',
	 * 	age:25,
	 * 	email:{
	 * 		first:'a.nate.com',
	 * 		second:'b.naver.com'
	 * 		}
	 * }
	 * 
	 * java에서는 json데이터가 객체존재한다.
	 * String data = "{
	 * 	\"no\":\"1\",
	 * 	\"name\":\"hong\"
	 * }"*/
	@GetMapping("/ajaxJson")
	@ResponseBody
	public String ajaxJson() {
		
		JSONObject jsonData = new JSONObject();
		jsonData.put("name", "gildong");
		jsonData.put("age", 25);
		
		JSONObject emailJson = new JSONObject();
		emailJson.put("first", "a.nate.com");
		emailJson.put("second", "b.naver.com");
		
		jsonData.put("email", emailJson);
		
		System.out.println(jsonData.toString());
		return jsonData.toString();
	}
}
