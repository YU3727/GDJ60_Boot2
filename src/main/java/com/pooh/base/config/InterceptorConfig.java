package com.pooh.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pooh.base.interceptors.AdminCheckInterceptor;
import com.pooh.base.interceptors.MemberCheckInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	//interceptor를 전문적으로 등록하는 설정 파일.
	
	//객체 가져오기
	@Autowired
	private MemberCheckInterceptor memberCheckInterceptor;
	
	@Autowired
	private AdminCheckInterceptor adminCheckInterceptor;
	
	//addinterceptor 오버라이딩으로 구현
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//어떤 url이 왔을 때 어떤 인터셉터를 실행할건지 등록
		
		//메서드 체이닝(메서드를 연결연결해서 사용)
		//memberCheckInterceptor 실행
		registry.addInterceptor(memberCheckInterceptor) //해당 인터셉터를
					.addPathPatterns("/member/mypage") //언제(어떤 url이 왔을 때) 실행할거냐
					.addPathPatterns("/qna/add")
					
					
					;
//					.excludePathPatterns("/member/login") //제외할 경로 설정
		
		//adminCheckInterceptor 실행
		registry.addInterceptor(adminCheckInterceptor)
					.addPathPatterns("/member/admin");
	}
}
