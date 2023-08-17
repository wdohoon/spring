package com.smart.myapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 1. View 페이지 파일이 있는 경우 ModelAndView객체를 만들어 리턴해준다.
// 2. View 페이지는 없으나 프론트엔드언어(HTML/CSS/JAVASCRIPT)를 사용하기 위해서는 ResponseEntity 객체를 만들어 기술한다.
// 3. ajax 처리 뷰가 필요없는 경우 @ResponseBody를 기술하지 않아도 된다.
@RestController
public class AjaxRestController {
	// DTO
	@GetMapping("/ajaxObjectRest")
	public BoardDTO javaObjectRest(){
		BoardDTO dto = new BoardDTO();
		dto.setNo(200);
		dto.setUserid("gildong");
		dto.setSubject("Rest컨트롤러 테스트");
		
		return dto;
	}
	// List
	@GetMapping("/ajaxListRest")
	public List<TestDTO> ajaxListRest() {
		List<TestDTO> list = new ArrayList<TestDTO>();
		
		list.add(new TestDTO(100,"홍길동","010-1111-1111","서초구"));
		list.add(new TestDTO(200,"이순신","010-2222-2222","관악구"));
		list.add(new TestDTO(300,"안중근","010-3333-3333","강남구"));
		list.add(new TestDTO(400,"아리랑","010-4444-4444","송파구"));
		
		return list;
	}
}
