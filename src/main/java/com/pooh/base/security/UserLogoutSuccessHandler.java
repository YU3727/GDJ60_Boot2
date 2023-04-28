package com.pooh.base.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.pooh.base.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Autowired
	private MemberVO memberVO;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		log.error("============ 로그아웃 성공 =============");
		
		//이렇게 하면 memberVO를 받아오지 못함. -> 자세한 내용은 230428 **사용자가 직접 객체를 생성하면 Annotation이 작동할까? 부분 참고
		log.error("=============={}=============", memberVO);
		
		//응답 보내기
		response.sendRedirect("/");
	}

	
}
