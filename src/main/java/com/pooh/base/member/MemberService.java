package com.pooh.base.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	//login
	public MemberVO getrLogin(MemberVO memberVO) throws Exception{
		//입력받는 memberVO에는 username, password만 있음
		MemberVO result = memberDAO.getLogin(memberVO);
		
		return memberDAO.getLogin(memberVO);
		
//		//pw check - Service 에서 pw 체크를 하는 경우
//		if(result != null && memberVO.getPassWord().equals(result.getPassWord())) {
//			//password를 null로
//			memberVO.setPassWord(null);
//			//입력받은 id로 member 정보 조회한 결과(result)의 역할정보를 memberDTO에 담기
//			memberVO.setRoleVOs(result.getRoleVOs());
//			//일치하면 정보를 담은 memberVO 리턴
//			return memberVO;
//		}else {
//			return null;
//		}
	}
	
	
	//join
	public int setJoin(MemberVO memberVO) throws Exception{
		//순서 : 1.입력된 정보 db에 insert, 2.입력된 정보에 기본 회원등급 member 추가해주기
		
		//1.
		int result = memberDAO.setJoin(memberVO);
		
		//2.
		result = memberDAO.setBasicRole(memberVO);
		
		return result;
	}
}
