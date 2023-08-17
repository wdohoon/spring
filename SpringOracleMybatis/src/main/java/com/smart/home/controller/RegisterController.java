package com.smart.home.controller;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.home.dto.RegisterDTO;
import com.smart.home.service.RegisterService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	// Service Interface를 객체로 생성해주는 방법
	// @Inject
	@Autowired // 객체를 생성하여 DI해준다.
	RegisterService service;
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
	@GetMapping("/regForm")
	public String regForm() {
		return "register/registerForm";
	}
	// 회원가입
	@PostMapping("/registerOk")
	public ModelAndView regOk(RegisterDTO dto) {
		// 데이터 request
		// DB insert
		int result=0;
		try {
			result = service.registerInsert(dto);
		}catch(Exception e) {
			System.out.println("회원가입실패...."+e.getMessage());
		}
		
		ModelAndView mav = new ModelAndView();
		if(result>0){// 1: 성공 : 홈으로
			// 컨트롤러 매피에서 다른 매핑주소 이동
			mav.setViewName("redirect:/");
		}else {// 0:실패 시 회원가입으로 이동
			mav.setViewName("register/registerResult");
		}
		
		return mav;
	}
	// 로그인 폼
	@GetMapping("/login")
	public String login() {
		return "register/login";
	}
	// 로그인 DB조회 - 아이디, 이름
	@PostMapping("/loginOk")
	public ModelAndView loginOk(String userid, String userpwd, HttpSession session) {
		
		// dto 일치하는 정보가 있으면 아이디, 이름
		// dto 일치하는 정보가 없으면 null
		RegisterDTO dto = service.loginOk(userid, userpwd);
		
		ModelAndView mav = new ModelAndView();
		if(dto!=null) {// 성공
			// 세션에 아이디, 이름, 로그인상태 기록
			session.setAttribute("logId", dto.getUserid());
			session.setAttribute("logName", dto.getUsername());
			session.setAttribute("logStatus", "Y");
			
			mav.setViewName("redirect:/");
		}else {// 실패
			// 로그인 폼으로 이동
			mav.setViewName("redirect:login");
		}
		return mav;
	}
	// 로그아웃 : session객체를 지운다.
	// 로그아웃 : session영역에 보관된 변수가 지워지고 새로운 session이 할당된다.
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		
		session.invalidate(); // session객체를 지운다. -> 새로운 session객체가 할당된다.
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		
		return mav;
	}
	// 아이디 찾기(폼)
	@GetMapping("/idSearch")
	public String idSearchForm() {
		return "register/idSearch";
	}
	@PostMapping("/idSearchOk")// ajax
	@ResponseBody
	public String idSearchOk(RegisterDTO dto) {
		
		// 이름, 연락처 일치하는 아이디와 이메일을 구한다.
		RegisterDTO resultDTO = service.idSearch(dto);
		String resultTxt = "N";
		if(resultDTO!=null) {// 일치하는 정보가 있을때
			// 이메일 보내기
			
			String subject ="아이디 찾기 결과";
			String content = "<div style='background:pink;"
					+ "border:1px solid #ddd;"
					+ "padding:50px;"
					+ "text-align:center'>";
			content += "검색한 아이디는 :"+ resultDTO.getUserid();
			content += "</div>";
			try {
			MimeMessage message =mailSender.createMimeMessage();
			MimeMessageHelper messageHelper= new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setFrom("ehgnsdnjs98@naver.com");// 보내는사람
			messageHelper.setTo("ehgnsdnjs98@naver.com");// 받는사람
			messageHelper.setSubject(subject);
			messageHelper.setText("text/html; charset=UTF-8",content);
			mailSender.send(message);
			
			resultTxt = "Y";
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {// 일치하는 정보가 없을때
			resultTxt = "N";
		}	
		return resultTxt;
	}
}
