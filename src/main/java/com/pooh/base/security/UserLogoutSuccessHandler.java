package com.pooh.base.security;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.pooh.base.member.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
	//이 로그아웃 핸들러는 우리가 하는 로그아웃도 사용하고, 소셜로그인한 사용자도 로그아웃할 때 쓴다.
	
	@Autowired
	private MemberDAO memberDAO;
	
	//properties에 있는 value값을 가져오는 방식.
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String restKey;
	


	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		log.error("============ 로그아웃 성공 =============");
		
		//이렇게 하면 memberDAO를 받아오지 못함. -> 자세한 내용은 230428 **사용자가 직접 객체를 생성하면 Annotation이 작동할까? 부분 참고
		log.error("=============={}=============", memberDAO);
		
		log.error(":: key ::::: {}", restKey);
		
		
		
		response.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id="+restKey+"&logout_redirect_uri=http://localhost/");
		
		
		//응답 보내기
		//response.sendRedirect("/");
		
	}

	
	
	

	
}
