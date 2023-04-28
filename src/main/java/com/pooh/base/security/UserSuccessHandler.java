package com.pooh.base.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.pooh.base.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.error("============로그인 성공시 작동=============");
		//확인 결과. 이 메서드까지 잘 들어온다. 아래쪽에 수행하고싶은 작업을 작성하면 됨.
		
		//ID 기억하기
		//체크 했는지 안했는지는 remember라는 이름으로 보냄. 파라미터는 request에 들어있다.
		String remember = request.getParameter("remember");
		log.error("============={}==============", remember);
		//확인 결과 체크하면 remember, 안하면 null이 옴
		
		//ID 기억하기 체크 되어있으면 쿠키 만들고 name, value 입력하기
		if(remember != null && remember.equals("remember")) {
			//1. member 객체는 authentication에 있음.
			//MemberVO memberVO = (MemberVO)authentication.getPrincipal();
			//Cookie cookie = new Cookie("remember", memberVO.getUsername())
			
			//2. username은 authentication에서 바로 꺼낼 수 있음.
			Cookie cookie = new Cookie("remember", authentication.getName());
			
			//쿠키 유효기간 설정(기본단위 초)
			cookie.setMaxAge(60*60*24);
			
			//쿠키를 응답에 추가하기
			response.addCookie(cookie);
		}else {
			//ID 기억하기를 다시 체크하지 않은 경우 1.기존의 쿠키가 있는지 확인, 2.있으면 쿠키의 maxAge값을 0으로 설정
			
			//1.요청에 대한 모든정보는 request에 들어있다. request에서 cookie를 꺼냄.
			Cookie[] cookies = request.getCookies();
			
			for(Cookie cookie : cookies) {
				//remember라는 이름의 쿠키가 있다면, 쿠키를 삭제하자
				if(cookie.getName().equals("remember")) {
					cookie.setMaxAge(0);
					
					//사용자의 web browser에 maxAge를 0으로 설정한 cookie를 다시 보내준다.
					response.addCookie(cookie);
					break;
				}
			}
		}
		
		//응답 보내기
		//foward
		//RequestDispatcher view = request.getRequestDispatcher("jsp 풀 경로명");
		//view.forward(request, response);
		
		//redirect
		//response.sendRedirect("url 경로명");
		response.sendRedirect("/");
		
	}

}
