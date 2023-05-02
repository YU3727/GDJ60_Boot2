<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Modern Business - Start Bootstrap Template</title>
    <!-- css/favicon -->
    	<c:import url="../temp/style.jsp"></c:import>
    <!-- css/favicon -->
</head>
<body class="d-flex flex-column h-100">
    <main class="flex-shrink-0">
    	<!-- Navigation-->
			<c:import url="../temp/header.jsp"></c:import>
		<!-- Nav 끝 -->
		
            <!-- Page content-->
            <section class="py-5">
                <div class="container px-5">
                    <!-- Contact form-->
                    <div class="bg-light rounded-3 py-5 px-4 px-md-5 mb-5">
                        <div class="text-center mb-5">
                            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-envelope"></i></div>
                            <h1 class="fw-bolder">Are you ready?</h1>
                            <p class="lead fw-normal text-muted mb-0">We've been wating for you</p>
                        </div>
                        <div class="row gx-5 justify-content-center">
                            <div class="col-lg-8 col-xl-6">
                                <!-- * * * * * * * * * * * * * * *-->
                                <!-- * * SB Forms Contact Form * *-->
                                <!-- * * * * * * * * * * * * * * *-->
                                <!-- This form is pre-integrated with SB Forms.-->
                                <!-- To make this form functional, sign up at-->
                                <!-- https://startbootstrap.com/solution/contact-forms-->
                                <!-- to get an API token!-->
                                
                                <!-- 로그인에 실패했을 경우 -->
                                <!-- 파라미터를 jsp에서도 바로 꺼낼 수 있다. param.~ -->
                                <c:if test="${not empty param.errorMessage}">
                                	<h1>${param.errorMessage}</h1>
                                </c:if>
                                
                                <form action="./login" id="loginForm" method="post" data-sb-form-api-token="API_TOKEN">
                                	<!-- 서버에서 발행한 CSRF 토큰을 같이 보내준다. -->
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                	<!-- 또는 Spring Security 태그를 사용하면 된다. -->
                                	<!-- <sec:csrfInput/> -->
                                    <!-- userName input-->
                                    <div class="form-floating mb-3">
                                        <input name="username" class="form-control" id="username" value="${cookie.remember.value}" type="text" data-sb-validations="required" />
                                        <label for="username">USERNAME</label>
                                    </div>
                                    
                                    <!-- password input-->
                                    <div class="form-floating mb-3">
                                        <input name="password" class="form-control" id="password" type="password" data-sb-validations="required" />
                                        <label for="password">PASSWORD</label>
                                    </div>
                                    
                                    <!-- password input-->
                                    <div class="mb-3">
                                        <input name="remember" class="" id="remember" value="remember" type="checkbox" data-sb-validations="required" />
                                        <label for="remember">ID 기억하기</label>
                                    </div>
                                    
                                    <!-- Login btn -->
                                    <div class="d-flex justify-content-between form-floating mb-3">
                                        <button type="button" id="loginBackBtn" class="feature bg-danger bg-gradient text-white rounded-3">X</button>   
                                        <button type="button" id="loginBtn" class="feature bg-primary bg-gradient text-white rounded-3">O</button>
                                    </div>
                                </form>
                                
                                <div class="d-flex justify-content-evenly">
                                <a href="./findPassword" class="btn btn-info">비밀번호 찾기</a>
                                <a href="/oauth2/authorization/kakao" class="btn btn-warning">카카오 로그인</a>
                                <!-- id, email 입력창 확인 -->                       
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
    
    </main>
    <!-- Footer 적용 -->
     	<c:import url="../temp/footer.jsp"></c:import>
  	<!-- Footer 끝 -->
  	<script type="text/javascript" src="../js/member.js"></script>
</body>
</html>