package com.pooh.base.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLoginFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		//작동 확인
		log.error("===== {} =====", exception);
		log.error("===== {} =====", exception.getMessage());
		
		//로그인이 실패하면 어떤이유로 실패했는지 알 수 있음. -> 사용자에게 어떤부분이 문제가 되는지 메시지를 보낼 수 있다.
		//Exception type이 뭔지에 따라 보낼 메시지를 준비하기.
		
		
		//에러메시지 준비 - MemberVO의 증명값을 false로 바꾸고 exception 메시지를 확인한 후 작성함.
		String errorMessage = "";
		
		//참조변수명 instanceof 클래스명 : 참조변수명이 클래스명 타입이 맞습니까? 라고 물어보는 것
		if(exception instanceof BadCredentialsException) {
			errorMessage="비밀번호가 일치하지 않습니다.";
		}else if(exception instanceof InternalAuthenticationServiceException) {
			errorMessage="ID가 일치하지 않습니다.";
		}else if(exception instanceof DisabledException) {
			//계정의 사용 여부 - enabled = false
			errorMessage="유효하지 않은 사용자입니다.";
		}else if(exception instanceof CredentialsExpiredException) {
			//패스워드의 만료 여부 - CredentialsNonExpired = false
			errorMessage="자격 증명 유효 기간이 만료되었습니다.";
		}else if(exception instanceof LockedException) {
			//계정의 잠김 여부 - AccountNonLocked = false
			errorMessage="사용자 계정이 잠겨 있습니다.";
		}else if(exception instanceof AccountExpiredException) {
			//계정의 만료 여부 - AccountNonExpired = false
			errorMessage="사용자 계정의 유효 기간이 만료 되었습니다.";
		}else {
			//기타 사항
			errorMessage="로그인에 실패했습니다.";
		}
		
		//위의 계정잠김 사용처
		//회사의 근태시간, 퇴직유무, 휴가유무 등에 따라 사용자의 회사서버 접근을 허용하고 막는권한을 줌.
		//자동으로 하고 싶은 경우(9-6 근무시간에만 접근 가능하게 하는 등) 등에는 스케쥴(schedule) 사용
		
		//응답 보내기
		errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
		response.sendRedirect("./login?errorMessage="+errorMessage);
	}
}
