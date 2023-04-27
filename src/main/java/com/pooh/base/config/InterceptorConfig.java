package com.pooh.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.pooh.base.interceptors.AdminCheckInterceptor;
import com.pooh.base.interceptors.MemberCheckInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	//interceptor를 전문적으로 등록하는 설정 파일.
	
	//객체 가져오기
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;
	
	@Autowired
	private MemberCheckInterceptor memberCheckInterceptor;
	
	@Autowired
	private AdminCheckInterceptor adminCheckInterceptor;
	
	//addinterceptor 오버라이딩으로 구현
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//어떤 url이 왔을 때 어떤 인터셉터를 실행할건지 등록
		
		//순서가 있다. 등록된 순서대로 실행함.(중요)
		
		//message Interceptor 등록
		registry.addInterceptor(localeChangeInterceptor)
					.addPathPatterns("/**");
		
		//메서드 체이닝(메서드를 연결연결해서 사용)
		//memberCheckInterceptor 실행
//		registry.addInterceptor(memberCheckInterceptor) //해당 인터셉터를
//					.addPathPatterns("/member/mypage") //언제(어떤 url이 왔을 때) 실행할거냐
//					.addPathPatterns("/qna/*")
//					.excludePathPatterns("/qna/list")
//					.addPathPatterns("/member/admin") //admin 체크할 때 로그인 했는지 안했는지 체크
//					.addPathPatterns("/notice/*")
//					.excludePathPatterns("/notice/list")
//					.excludePathPatterns("/notice/detail")
					
					;
//					.excludePathPatterns("/member/login") //제외할 경로 설정
		
		//adminCheckInterceptor 실행
//		registry.addInterceptor(adminCheckInterceptor)
//					.addPathPatterns("/member/admin")
//					.addPathPatterns("/notice/*")
//					.excludePathPatterns("/notice/list")
//					.excludePathPatterns("/notice/detail")
//					
//					
//					;
		//memberCheck, adminCheck는 이제 Security에서 하게 된다.
	}
}
