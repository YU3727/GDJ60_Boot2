package com.pooh.base.member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class MemberDAOTest {

	@Autowired
	private MemberDAO memberDAO;
	
	@Test
	public void idDulpicateCheck() throws Exception{
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername("id2");
		
		memberVO = memberDAO.idDuplicateCheck(memberVO);
		if(memberVO == null) {
			log.error("===== 중복 아님 =====");
		}else if(memberVO != null) {
			log.error("===== 중복 =====");
		}
	}

}
