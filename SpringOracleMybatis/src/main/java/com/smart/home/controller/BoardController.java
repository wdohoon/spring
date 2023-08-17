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

// @Controller : 모델뷰를 리턴해준다.
// @Controller : ModelAndView,
// @Controller : Model, String

// @RestController : Model이 리턴된다.
//					 Model+viewPage -> ModelAndView로 리턴

@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService service;
	
	@GetMapping("/boardList")
	public ModelAndView boardList(PagingDTO pDTO) {
		
		// 총레코드 수
		pDTO.setTotalRecord(service.totalRecord(pDTO));
		// 해당페이지의 레코드 선택
		List<BoardDTO> list = service.boardList(pDTO);
		
		// ModelAndView
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.addObject("pDTO", pDTO);
		mav.setViewName("board/boardList");
		return mav;
	}
	// 글쓰기 폼으로 이동
	@GetMapping("/boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		return mav;
	}
	// 글쓰기 DB기록
	@PostMapping("/boardWriteOk")
	public  ResponseEntity<String> boardWriteOk(BoardDTO dto, HttpServletRequest request) {
		// HttpServletRequest -> request, HttpSession
		// HttpSession
		
		// no, hti, writedaste -> 오라클
		// userid -> 세션
		dto.setUserid((String)request.getSession().getAttribute("logId"));
		// ip -> request
		dto.setIp(request.getRemoteAddr());
		
		int result = 0;
		try {
			result = service.boardWriteOk(dto);
		}catch(Exception e) {
			System.out.println("게시판 글등록 예외발생 ...." + e.getMessage());
		}
		// 등록결과에 따른 스크립트를 생성한다.
		String tag = "<script>";
		if(result>0) { // 성공->게시판 목록
			tag += "location.href='/home/board/boardList';";
		}else { // 실패->글등록폼으로
			tag += "alert('글등록이 실패했습니다.');";
			tag += "history.back();";
		}
		tag += "</script>";
		// ResponseEntity 객체는 프론트페이지를 작성할 수 있다.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("text", "html", Charset.forName("UTF-8")));
		return new ResponseEntity<String>(tag, headers, HttpStatus.OK);
	}
	// 글내용보기
	@GetMapping("/boardView")
	public ModelAndView boardView(int no, PagingDTO pDTO) {
		// 조회수 증가
		service.hitCount(no);
		// 레코드선택
		BoardDTO dto = service.getBoard(no);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.addObject("pDTO", pDTO);
		
		mav.setViewName("board/boardView");
		
		return mav;
	}
	// 글수정폼
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
		if(result>0){ // 글수정성공 -> 글내용보기
			mav.setViewName("redirect:boardView");
		}else { // 글수정실패 -> 수정폼으로
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	// 글삭제
	@GetMapping("/boardDel")
	public ModelAndView boardDel(int no, HttpSession session) {
		int result = service.boardDel(no, (String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {// 삭제시 -> 목록
			mav.setViewName("redirect:boardList");
		}else {// 삭제실패 -> 글내용
			mav.addObject("no", no);
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}

}
