package com.smart.home.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.home.dto.ReplyDTO;
import com.smart.home.service.ReplyService;

@RestController
public class ReplyController {
	@Autowired
	ReplyService service;
	
	// 댓글등록
	@PostMapping("/reply/replyWrite")
	public String replyWrite(ReplyDTO dto, HttpSession session) {
		//session글쓴이 구하기
		dto.setUserid((String)session.getAttribute("logId"));
		int result = service.replyInsert(dto);
		return result+"";
	}
	// 댓글목록
	@GetMapping("/reply/replyList")
	public List<ReplyDTO> replyList(int no) {// 원글글번호
		return service.replySelect(no);
	}
	// 댓글 수정(DB를 수정하기 때문에 update문을 사용한다.)
	@PostMapping("/reply/replyEditOk")
	public String replyEditOk(ReplyDTO dto) {
		return String.valueOf(service.replyUpdate(dto));
	}
	// 댓글 삭제
	@GetMapping("/reply/replyDel")
	public String replyDel(int re_no) {
		return String.valueOf(service.replyDelete(re_no));
	}
}
