package com.smart.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// interceptor 처리를 할 클래스는 반드시 HandlerInterceptorAdapter를 상속받는다. 
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	// 매핑되기 전에 호출되는 interceptor
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		// 현재 로그인 유무를 확인해서 로그인이 된 경우 원래 매핑주소로 이동하고 
		// 로그인이 안된 경우 로그인폼으로 매핑되도록 매핑주소를 변경한다.
		
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("logId");
		String logStatus = (String)session.getAttribute("logStatus");
		
		if(userid==null || logStatus==null || userid.equals("") || !logStatus.equals("Y")) {
			// 로그인 안된경우
			response.sendRedirect("/home/register/login");
			return false; // 원래 매핑주소로 가지 않고 로그인 매핑으로 이동한다.
		}
		// 로그인 된경우
		return true; // 원래패밍으로 이동한다.
	}
	
	// View페이지로 이동전에 interceptor
	
	// 매핑이 실행된 후 interceptor
}
