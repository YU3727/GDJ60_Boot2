package com.pooh.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

//일반적인 자바 객체가 아니라 설정파일임을 나타내는 객체
@Configuration
@Slf4j
//***-context.xml
public class WebConfig implements WebMvcConfigurer{

	//legacy에서 servlet-context.xml에 작성되어있는 내용 작성
	//파일 다운로드를 위한 자원들의 경로를 여기에 표시 해준다.
	
	@Value("${app.upload.base}")
	private String basePath;
	
	@Value("${app.url.path}")
	private String urlPath;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		//<resources mapping="/resources/**" location="/resources/" /> //연결 작업(같은 의미의 코드)
		//<resources mapping="/file/**" location="D://production/upload">
		registry.addResourceHandler(urlPath)
				.addResourceLocations(basePath);
	}
	
}
