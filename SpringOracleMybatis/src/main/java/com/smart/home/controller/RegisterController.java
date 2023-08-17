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
	// Service Interface�� ��ü�� �������ִ� ���
	// @Inject
	@Autowired // ��ü�� �����Ͽ� DI���ش�.
	RegisterService service;
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
	@GetMapping("/regForm")
	public String regForm() {
		return "register/registerForm";
	}
	// ȸ������
	@PostMapping("/registerOk")
	public ModelAndView regOk(RegisterDTO dto) {
		// ������ request
		// DB insert
		int result=0;
		try {
			result = service.registerInsert(dto);
		}catch(Exception e) {
			System.out.println("ȸ�����Խ���...."+e.getMessage());
		}
		
		ModelAndView mav = new ModelAndView();
		if(result>0){// 1: ���� : Ȩ����
			// ��Ʈ�ѷ� ���ǿ��� �ٸ� �����ּ� �̵�
			mav.setViewName("redirect:/");
		}else {// 0:���� �� ȸ���������� �̵�
			mav.setViewName("register/registerResult");
		}
		
		return mav;
	}
	// �α��� ��
	@GetMapping("/login")
	public String login() {
		return "register/login";
	}
	// �α��� DB��ȸ - ���̵�, �̸�
	@PostMapping("/loginOk")
	public ModelAndView loginOk(String userid, String userpwd, HttpSession session) {
		
		// dto ��ġ�ϴ� ������ ������ ���̵�, �̸�
		// dto ��ġ�ϴ� ������ ������ null
		RegisterDTO dto = service.loginOk(userid, userpwd);
		
		ModelAndView mav = new ModelAndView();
		if(dto!=null) {// ����
			// ���ǿ� ���̵�, �̸�, �α��λ��� ���
			session.setAttribute("logId", dto.getUserid());
			session.setAttribute("logName", dto.getUsername());
			session.setAttribute("logStatus", "Y");
			
			mav.setViewName("redirect:/");
		}else {// ����
			// �α��� ������ �̵�
			mav.setViewName("redirect:login");
		}
		return mav;
	}
	// �α׾ƿ� : session��ü�� �����.
	// �α׾ƿ� : session������ ������ ������ �������� ���ο� session�� �Ҵ�ȴ�.
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		
		session.invalidate(); // session��ü�� �����. -> ���ο� session��ü�� �Ҵ�ȴ�.
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		
		return mav;
	}
	// ���̵� ã��(��)
	@GetMapping("/idSearch")
	public String idSearchForm() {
		return "register/idSearch";
	}
	@PostMapping("/idSearchOk")// ajax
	@ResponseBody
	public String idSearchOk(RegisterDTO dto) {
		
		// �̸�, ����ó ��ġ�ϴ� ���̵�� �̸����� ���Ѵ�.
		RegisterDTO resultDTO = service.idSearch(dto);
		String resultTxt = "N";
		if(resultDTO!=null) {// ��ġ�ϴ� ������ ������
			// �̸��� ������
			
			String subject ="���̵� ã�� ���";
			String content = "<div style='background:pink;"
					+ "border:1px solid #ddd;"
					+ "padding:50px;"
					+ "text-align:center'>";
			content += "�˻��� ���̵�� :"+ resultDTO.getUserid();
			content += "</div>";
			try {
			MimeMessage message =mailSender.createMimeMessage();
			MimeMessageHelper messageHelper= new MimeMessageHelper(message,true,"UTF-8");
			messageHelper.setFrom("ehgnsdnjs98@naver.com");// �����»��
			messageHelper.setTo("ehgnsdnjs98@naver.com");// �޴»��
			messageHelper.setSubject(subject);
			messageHelper.setText("text/html; charset=UTF-8",content);
			mailSender.send(message);
			
			resultTxt = "Y";
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {// ��ġ�ϴ� ������ ������
			resultTxt = "N";
		}	
		return resultTxt;
	}
}
