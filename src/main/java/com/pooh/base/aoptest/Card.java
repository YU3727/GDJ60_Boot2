package com.pooh.base.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class Card {

	//AOP용어로 advice(핵심 로직 전 실행되어야 할 공통 로직)
	//교통카드 찍기 메서드
	//모든리턴타입(*), point cut은 핵심로직이므로 메서드이므로 끝을 소괄호()로 마무리.
	//@Before("execution(* com.pooh.base.aoptest.Transport.use*())")
	//@AfterReturning("execution(* com.pooh.base.aoptest.Transport.use*())")
	//@AfterThrowing("execution(* com.pooh.base.*.*Service.set*(..))") // 트랜젝션 처리
		//execution : 어떤 point-cut을 선택하느냐 라는 선택자 같은 느낌.
	//@Around("execution(* com.pooh.base.aoptest.Transport.use*(..))") //매개변수의 타입과 갯수상관이 없을때는 (..)
	public Object cardCheck(ProceedingJoinPoint joinPoint) throws Throwable{
		//around를 쓸 때는 한가지 더 매개변수가 필요함.(ProceedingJoinPoint)
		//point-cut(useBus, useSubway..)메서드를 객체화 하고, joinPoint라는 이름에 저장.
		//joinPoint.proceed()는 예외처리를 해야하므로, exception보다 상위 예외처리인 Throwable로 처리한다.
		
		log.error("탑승입니다.");
		
		//무슨 타입이 올지 모르므로, 최상위 타입인 오브젝트로 선언해서 매개변수를 받아봄.
		//**args : 매개변수(arguments)
		Object[] objs = joinPoint.getArgs();
		for(Object ob : objs) {
			log.warn("args =============> {}", ob.toString());
		}
		
		Object obj = joinPoint.proceed();
		log.error("하차입니다.");
		
		//실행하는 point-cut(useSubway, useBus..)의 return 값을 받아서 원래 실행 되는 곳인 homeController로 넘기기 위해서 return을 해줘야한다.
		log.warn("OBJECT ======> {}", obj.toString());
		
		return obj;
	}
	
}
