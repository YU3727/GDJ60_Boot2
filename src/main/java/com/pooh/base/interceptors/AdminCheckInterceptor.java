package com.pooh.base.interceptors;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.pooh.base.member.MemberVO;
import com.pooh.base.member.RoleVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * admin 유무를 판별하는 Interceptor
 *
 */

@Component
@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor{

	//admin 권한인지 확인
	@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
		//권한 여부 확인용 변수
		//boolean checkAdmin = false;
		
		log.info("========== admin check Interceptor proceeding... ==========");
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("member");
		
		//로그인 했으면 interceptor, 안했으면 로그인하게 어케하지?
//		if(memberVO != null) {
			//사용자 1명당 권한이 2개 이상일수도 있으므로, 반복문으로 확인
			for(RoleVO roleVO : memberVO.getRoleVOs()) {
				if(roleVO.getRoleName().equals("ROLE_ADMIN")) {
					//admin이 맞다
					return true;
					//checkAdmin = true;
					//break;
				}
//				else {
				//admin이 아니다
				//결과 보여주기 - 옛날 방식의 fowarding
				//model에 담을 내용
//				checkAdmin = false;
//				}
			}
			request.setAttribute("message", "권한이 없습니다");
			request.setAttribute("url", "/");
			
			//view 경로 설정
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/result.jsp");
			view.forward(request, response);
			
			return false;
			
//		}else {
//			//로그인 안했으면
//			//model에 담을 내용 설정(메시지, url)
//			request.setAttribute("message", "로그인이 필요합니다.");
//			request.setAttribute("url", "/member/login");		
//			
//			//view 경로 설정
//			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/result.jsp"); //jsp 경로 쓰기. prefix, suffinx가 자동으로 붙지 않기 때문에 경로를 다 써줘야함.
//			view.forward(request, response);
//			
//			checkAdmin = false;
//		}
		
//		return checkAdmin;
		
		
		}
}
