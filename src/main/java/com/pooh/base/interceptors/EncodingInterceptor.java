package com.pooh.base.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class EncodingInterceptor implements HandlerInterceptor {

	//나중에 쓸거면 Annotation 입력
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		//같은 url이 입력되었을 때(add, update) 검증이 필요한 경우와 필요하지 않은 경우를 구별할 수 있을까?
		//1. method형식 (Request에서 method 형식을 꺼내면 됨. request.getMethod())
		//2. 아니면 url 자체로 구분하는 것도 가능.
		
		//delete의 경우에는 service에서 작업하는게 좋다. 또는 Interceptor에 dao에 DI(의존성 주입)을 하고 사용해도 되지만, 복잡해진다.
		//preHandle에서는 글번호 정보만 있고, postHandle에서는 이미 DB에 글을 지우고 나서 결과를 가져오기밖에 안되기 때문에 애매하다.
		// -> SERVICE에서 처리
	}
}
