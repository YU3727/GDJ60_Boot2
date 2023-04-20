package com.pooh.base.aoptest;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.pooh.base.board.BoardFileVO;
import com.pooh.base.util.Pager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Transport {
	
	//AOP용어로 point-cut(핵심 로직)
	//출근시 타고다니는 교통수단(매번 반복되는 것)
	public int useSubway(BoardFileVO boardFileVO) throws Exception{
		//예외 발생을 위한 구문
//		Random random = new Random();
//		int num = random.nextInt(2);
//		if(num==0){
//			throw new Exception();
//		}
		log.error("지하철 이용");
		return 5;
	}
	
	public String useBus(Pager pager) {
		log.error("버스 이용");
		return "BUS";
	}
	
	public void takeWalk() {
		log.error("걸어가기");
	}
	
	//교통수단을 이용하기전에 카드를 찍어야 하는데, 이 작업은 동일한 작업을 매번 반복해야한다.
	//OOP: 동일한 일을 하는 것들을 하나의 클래스나 메서드를 만들어서 넣어놓고 사용하자.
	
	//셋 다 핵심기능은 맞지만 지하철 버스이용은 실행 전 카드를 찍어야하고, 걸어가기는 필요없다.
	
}
