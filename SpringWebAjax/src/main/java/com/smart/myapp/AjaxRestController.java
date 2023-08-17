package com.smart.myapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 1. View ������ ������ �ִ� ��� ModelAndView��ü�� ����� �������ش�.
// 2. View �������� ������ ����Ʈ������(HTML/CSS/JAVASCRIPT)�� ����ϱ� ���ؼ��� ResponseEntity ��ü�� ����� ����Ѵ�.
// 3. ajax ó�� �䰡 �ʿ���� ��� @ResponseBody�� ������� �ʾƵ� �ȴ�.
@RestController
public class AjaxRestController {
	// DTO
	@GetMapping("/ajaxObjectRest")
	public BoardDTO javaObjectRest(){
		BoardDTO dto = new BoardDTO();
		dto.setNo(200);
		dto.setUserid("gildong");
		dto.setSubject("Rest��Ʈ�ѷ� �׽�Ʈ");
		
		return dto;
	}
	// List
	@GetMapping("/ajaxListRest")
	public List<TestDTO> ajaxListRest() {
		List<TestDTO> list = new ArrayList<TestDTO>();
		
		list.add(new TestDTO(100,"ȫ�浿","010-1111-1111","���ʱ�"));
		list.add(new TestDTO(200,"�̼���","010-2222-2222","���Ǳ�"));
		list.add(new TestDTO(300,"���߱�","010-3333-3333","������"));
		list.add(new TestDTO(400,"�Ƹ���","010-4444-4444","���ı�"));
		
		return list;
	}
}
