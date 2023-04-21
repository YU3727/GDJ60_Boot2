package com.pooh.base.member;

import java.util.HashMap;
import java.util.Map;

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
		//순서 : 1.입력된 정보 db에 insert, 2.입력된 정보에 기본 회원등급 member 추가해주기, 3.enabled 기본값 입력(true)
		
		//1.
		int result = memberDAO.setJoin(memberVO);
		
		//2. memberVO 대신 Map을 사용해보기. map은 interface라 구현클래스인 hashMap을 사용
		//result = memberDAO.setBasicRole(memberVO);
		Map<String, Object> map = new HashMap<>();
		map.put("userName", memberVO.getUserName());
		map.put("num", 3);
		result = memberDAO.setBasicRole(map);
		
		//3.
		memberVO.setEnabled(true);
		
		return result;
	}
	
	//idDuplicateCheck
	public boolean idDuplicateCheck(MemberVO memberVO) throws Exception{
		memberVO = memberDAO.idDuplicateCheck(memberVO);
		
		boolean idCheck = true; //중복 아니면 true, 중복이면 false
		if(memberVO != null) {
			idCheck = false;
		}
		
		return idCheck;
	}
}
