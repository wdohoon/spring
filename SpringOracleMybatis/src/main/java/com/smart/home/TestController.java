package com.smart.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	// get�������
	//	/test�� ������ �����ϸ� ȣ��Ǵ� �޼ҵ�
	// @RequestMapping(value="/test",method=Request
	@RequestMapping("/test")
	public String test(int num, String name, Model model) {
		System.out.println("num=" + (num+200));
		System.out.println("name->"+name);
		
		// �������� Ŭ���̾�Ʈ (���������� ���� ������)
		model.addAttribute("name", name);
		model.addAttribute("tel","010-4503-2184");
		
		return "home";
	}
	@RequestMapping("/test2")
	public ModelAndView test2(HttpServletRequest req) {
		int no = Integer.parseInt(req.getParameter("no"));
		String id = req.getParameter("id");
		System.out.println(no+", "+id);
		
		// �����Ϳ� view ���������� �����ϴ� ��ü
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("num", no);
		mav.addObject("id", id);
		
		mav.setViewName("home");
		
		return mav;
	}
	// GET ��� �������� ���� ������̼�
	@GetMapping("/test3")
	public ModelAndView test3(TestDto dto) {
		System.out.println(dto.toString());
		dto.setNum(dto.getNum()+500);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		
		mav.setViewName("home");
		
		return mav;
	}
	// Post����� ����
	// @RequestMapping(value="/test4", method=RequestMethod.POST)
	@PostMapping("/test4")
	public String test4(@RequestParam("username") String username,
					    @RequestParam("addr") String address) {
		// post���
		System.out.println(username+", "+address);
		
		return "home";
	}
	// path�� ���Ե� ���� ����ϴ� ���
	@GetMapping("/test5/{page}")
	public String test5(@PathVariable("page")int page ) {
		System.out.println("page->" + page);
		return "home";
	}
}
