package com.smart.home.controller;


import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.smart.home.dto.BoardDTO;
import com.smart.home.dto.PagingDTO;
import com.smart.home.service.BoardService;

// @Controller : �𵨺並 �������ش�.
// @Controller : ModelAndView,
// @Controller : Model, String

// @RestController : Model�� ���ϵȴ�.
//					 Model+viewPage -> ModelAndView�� ����

@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService service;
	
	@GetMapping("/boardList")
	public ModelAndView boardList(PagingDTO pDTO) {
		
		// �ѷ��ڵ� ��
		pDTO.setTotalRecord(service.totalRecord(pDTO));
		// �ش��������� ���ڵ� ����
		List<BoardDTO> list = service.boardList(pDTO);
		
		// ModelAndView
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("pDTO", pDTO);
		mav.setViewName("board/boardList");
		return mav;
	}
	// �۾��� ������ �̵�
	@GetMapping("/boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		return mav;
	}
	// �۾��� DB���
	@PostMapping("/boardWriteOk")
	public  ResponseEntity<String> boardWriteOk(BoardDTO dto, HttpServletRequest request) {
		// HttpServletRequest -> request, HttpSession
		// HttpSession
		
		// no, hti, writedaste -> ����Ŭ
		// userid -> ����
		dto.setUserid((String)request.getSession().getAttribute("logId"));
		// ip -> request
		dto.setIp(request.getRemoteAddr());
		
		int result = 0;
		try {
			result = service.boardWriteOk(dto);
		}catch(Exception e) {
			System.out.println("�Խ��� �۵�� ���ܹ߻� ...." + e.getMessage());
		}
		// ��ϰ���� ���� ��ũ��Ʈ�� �����Ѵ�.
		String tag = "<script>";
		if(result>0) { // ����->�Խ��� ���
			tag += "location.href='/home/board/boardList';";
		}else { // ����->�۵��������
			tag += "alert('�۵���� �����߽��ϴ�.');";
			tag += "history.back();";
		}
		tag += "</script>";
		// ResponseEntity ��ü�� ����Ʈ�������� �ۼ��� �� �ִ�.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		return new ResponseEntity<String>(tag, headers, HttpStatus.OK);
	}
	// �۳��뺸��
	@GetMapping("/boardView")
	public ModelAndView boardView(int no, PagingDTO pDTO) {
		// ��ȸ�� ����
		service.hitCount(no);
		// ���ڵ弱��
		BoardDTO dto = service.getBoard(no);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.addObject("pDTO", pDTO);
		
		mav.setViewName("board/boardView");
		
		return mav;
	}
	// �ۼ�����
	@GetMapping("/boardEdit")
	public ModelAndView boardEdit(int no) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", service.getBoard(no));
		mav.setViewName("/board/boardEdit");
		
		return mav;
	}
	@PostMapping("/boardEditOk") // no, subject, content
	public ModelAndView boardEditOk(BoardDTO dto, HttpSession session) {
		dto.setUserid((String)session.getAttribute("logId"));
		
		int result = service.boardEdit(dto);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", dto.getNo());
		if(result>0){ // �ۼ������� -> �۳��뺸��
			mav.setViewName("redirect:boardView");
		}else { // �ۼ������� -> ����������
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	// �ۻ���
	@GetMapping("/boardDel")
	public ModelAndView boardDel(int no, HttpSession session) {
		int result = service.boardDel(no, (String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {// ������ -> ���
			mav.setViewName("redirect:boardList");
		}else {// �������� -> �۳���
			mav.addObject("no", no);
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}

}
