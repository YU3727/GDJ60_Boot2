package com.pooh.base.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.pooh.base.util.MailManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MemberService implements UserDetailsService{

	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MailManager mailManager;
	
	
	//UserDetailsService를 구현함으로써 생긴 메서드. Controller를 거치지 않고 바로 옴....
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//MemberVO는 userDetails를 구현했으므로, MemberVO를 리턴하라는 의미.
		
		//여기까지 들어오는지 확인
		log.error("================= Spring Security Login =================");
		log.error("================== username: {} =========================", username);
		
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername(username);
		try {
			memberVO = memberDAO.getLogin(memberVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberVO;
	}
	//이렇게 끝나면 이 메서드는 누가 호출하고 비밀번호 비교는 언제해??? -> 이 메서드를 호출한 Spring Security 객체가 함. 이 메서드는 유저의 정보만 return 하는 역할.
	//메서드가 memberVO를 리턴하면, Spring Security 객체는 사용자가 입력한 id,pw와 memberVO에 들어있는 id,pw를 꺼내서 비교해본다.
	//그래서 getLogin SQL문을 쓸 때 password도 꺼내와야함.(사용자가 입력한 값과 비교가 가능해야하기 때문에)

	
	//패스워드가 일치하는지, id중복은 없는지 검증하는 메서드 작성
	public boolean memberCheck(MemberVO memberVO, BindingResult bindingResult) throws Exception{
		//error 유무를 저장하는 클래스 BindingResult도 사용한다.
		boolean result = false;
		//false : 에러없음(검증성공)
		//true  : 에러있음(검증실패)
		
		//1.Annotation의 검증 결과
		//result 자체가 controller에서 한번 검증을 받은 결과를 가져옴.
		result = bindingResult.hasErrors();
		
		//2. password 일치 검증
		if(!memberVO.getPassword().equals(memberVO.getPasswordCheck())) {
			result = true;
			//어느 부분을 비교하고, 어느 설정에 등록된 내용을 쓸것인지 key값 입력
			//bindingResult.rejectValue("멤버변수명(path)", "properties의key(코드)");
			bindingResult.rejectValue("passwordCheck", "member.password.notEqual");
		}
		
		//3. ID중복 검사
		log.info("있을까? : {}", memberDAO.idDuplicateCheck(memberVO));
		if(memberDAO.idDuplicateCheck(memberVO)!=null) {
			result = true;
			bindingResult.rejectValue("username", "member.userName.duplicate");
		}
		
		return result;
	}
	
	//login
	public MemberVO getLogin(MemberVO memberVO) throws Exception{
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
	
	
	//logout
	public int setLogoutTime(MemberVO memberVO) throws Exception{
		return memberDAO.setLogoutTime(memberVO);
	}
	
	
	//join
	public int setJoin(MemberVO memberVO) throws Exception{
		//순서 : 1.입력된 정보 db에 insert, 2.입력된 정보에 기본 회원등급 member 추가해주기, 3.enabled 기본값 입력(true)
		
		//비밀번호 암호화까지 추가
		memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
		
		//1.
		int result = memberDAO.setJoin(memberVO);
		
		//2. memberVO 대신 Map을 사용해보기. map은 interface라 구현클래스인 hashMap을 사용
		//result = memberDAO.setBasicRole(memberVO);
		Map<String, Object> map = new HashMap<>();
		map.put("username", memberVO.getUsername());
		map.put("num", 3);
		result = memberDAO.setBasicRole(map);
		
		//3.
		//memberVO.setEnabled(true);
		
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
	
	
	//getFindPassword
	public int getFindPassword(MemberVO memberVO) throws Exception{
		
		int result = 0;
		memberVO = memberDAO.getFindPassword(memberVO);
		
		//memberVO가 null이 아니면 조회 성공, null이면 일치하는 사람 없음
		if(memberVO != null) {
			//조회 성공
			log.error("============={}, {}=============", memberVO.getUsername(), memberVO.getEmail());
			
			//email 내용 만들기
			String sub = memberVO.getUsername()+"님, test page 임시 비밀번호입니다.";
			
			//임시비밀번호 만들기(숫자+문자 6자리 난수발생기)
			StringBuffer sb = new StringBuffer();
			Random random = new Random();
			
			for(int i=0; i<6; i++) {
				int idx = random.nextInt(3);
				switch(idx) {
				case 0:
					//26 : a-z 알파벳 갯수
					//97 : 소문자 'a'의 ASCII 코드
					//(int)(random.nextInt(26)) + 97 : 랜덤 소문자 ASCII 코드
					sb.append((char)((int)(random.nextInt(26)) + 97));
					break;
				case 1:
					//65 : 대문자 'A'의 ASCII 코드
					//(int)(random.nextInt(26)) + 65 : 랜덤 대문자 ASCII 코드
					sb.append((char)((int)(random.nextInt(26)) + 65));
					break;
				case 2:
					//0~9 숫자 랜덤발생
					sb.append(random.nextInt(10));
				}
			}
			
			log.error("===={}====", sb);
			
			String con = "임시 비밀번호는 "+sb+" 입니다.";
			
			log.error("===={}====", memberVO.getEmail());
			log.error("===={}====", sub);
			log.error("===={}====", con);
			
			
			//db에 임시비밀번호 암호화 해서 저장하기.
			memberVO.setPassword(passwordEncoder.encode(sb));
			result = memberDAO.setTempPassword(memberVO);
			
			//email 발송하기
			if(result>0) {
				mailManager.send(memberVO.getEmail(), sub, con);
			}
			
		}else {
			//조회 실패
			log.error("==============memberVO is null=============");
		}
		
		return result;
	}
	
}
