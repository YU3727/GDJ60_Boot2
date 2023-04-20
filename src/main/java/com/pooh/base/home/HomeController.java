package com.pooh.base.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pooh.base.aoptest.Card;
import com.pooh.base.aoptest.Transport;
import com.pooh.base.board.BoardFileVO;
import com.pooh.base.util.Pager;

@Controller
public class HomeController {
	
	@Autowired
	private Transport transport;
	private Card card;

	@GetMapping("/")
	public String Home() {
		return "index";
	}
	
	//aopTest
	@GetMapping("/use")
	public void use() throws Exception{
		
		//아래의 상태 자체가 OOP 언어의 한계이다. 반복을 피하기 위해서 클래스/메서드를 부품으로 만들어서 사용하지만,
		//모아놓고 보니 또 반복되는 작업을 하게 됨.
		
		Pager pager = new Pager();
		pager.setKind("Bus Title");
		
		//버스 타기 (카드체크 - 버스탑승 - 카드체크 - 버스하차)
		//card.cardCheck();
		transport.useBus(pager);
		//card.cardCheck();
		
		BoardFileVO boardFileVO = new BoardFileVO();
		boardFileVO.setFileName("Subway File");
		
		//지하철 타기
		//card.cardCheck();
		transport.useSubway(boardFileVO);
		//card.cardCheck();
		
		//걸어 가기
		//card.cardCheck();
		transport.takeWalk();
		//card.cardCheck();
		
		
		//위 내용에서 핵심기능은 버스를 타고, 택시를 타는 것이다.
		//AOP는 핵심기능이 실행되면, 수반되는 부가작업들을 수행해주는 것이다.(OOP를 조금 더 OOP답게 만든다)
		//개발자는 스프링에게 뭐가 핵심기능인지 알려줘야한다.
	}
}
