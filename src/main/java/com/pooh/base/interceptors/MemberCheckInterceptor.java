package com.pooh.base.interceptors;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

// /**를 입력하고 ctrl+enter
/**
 * 
 * 로그인 유무를 판별하는 Interceptor
 *
 */

@Slf4j
@Component //객체만들기
public class MemberCheckInterceptor implements HandlerInterceptor{

	//로그인 됐는지 확인
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("==========Interceptor 실행==========");
		//내장객체의 lifeCycle이 짧은것에서 긴 것은 꺼낼 수 있음.(확정적으로 존재하기 떄문), 반대는 확정적으로 존재하지 않기때문에 꺼낼수없음.
		HttpSession session = request.getSession();
		Object memberVO = session.getAttribute("member");
		
		if(memberVO != null) {
			//로그인이 되었다. - 리턴 하고 끝.
			return true;
		}else {
			//redirect
			//response.sendRedirect("member/login");
			
			
			//옛날 방식의 fowarding을 해야한다.
			//model에 담을 내용 설정(메시지, url)
			request.setAttribute("message", "로그인이 필요합니다.");
			request.setAttribute("url", "/member/login");		
			
			//view 경로 설정
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/result.jsp"); //jsp 경로 쓰기. prefix, suffinx가 자동으로 붙지 않기 때문에 경로를 다 써줘야함.
			view.forward(request, response);
			
			return false;
		}
		
	}
}
