<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                            <h1 class="fw-bolder">Welcome!</h1>
                            <p class="lead fw-normal text-muted mb-0">Will you join us?</p>
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
                                <!-- form:form의 제일 중요한 속성 - ModelAttribute : 입력받을 VO를 적는다. // action을 생략하는경우 입력받은 url 그대로 감 -->
                                <form:form action="./join" method="post" modelAttribute="memberVO" id="joinForm">
                                	<!-- required는 html5에서만 동작하므로, 사용자의 환경이 html5가 아닌 경우 검증할 수 없다. 그래서 JavaScript로 다시 체크를 한다. -->
                                    <!-- userName input-->
                                    <div class="form-floating mb-3">
                                    	<form:input path="username" id="username" cssClass="form-control"/>
                                        <form:label path="username">USERNAME</form:label>
                                        <form:errors path="username"></form:errors>
                                    </div>
                                    <!-- password input-->
                                    <div class="form-floating mb-3">
                                    	<form:password path="password" id="password" cssClass="form-control"/>
                                        <form:label path="password">PASSWORD</form:label>
                                        <form:errors path="password"></form:errors>
                                    </div>
                                    <!-- passwordCheck input-->
                                    <div class="form-floating mb-3">
                                    	<form:password path="passwordCheck" id="passwordCheck" cssClass="form-control"/>
                                        <form:label path="passwordCheck">PASSWORD CHECK</form:label>
                                        <form:errors path="passwordCheck"></form:errors>
                                        
                                    </div>
                                    <!-- name input-->
                                    <div class="form-floating mb-3">
                                    	<form:input path="name" id="name" cssClass="form-control"/>
                                        <form:label path="name">NAME</form:label>
                                        <form:errors path="name"></form:errors>
                                    </div>
                                    <!-- email input-->
                                    <div class="form-floating mb-3">
                                    	<form:input path="email" id="email" cssClass="form-control"/>
                                        <form:label path="email">EMAIL</form:label>
                                        <form:errors path="email"></form:errors>
                                    </div>
                                    <!-- birth input-->
                                    <div class="form-floating mb-3">
                                    	<!-- form:date는 없으므로 그냥 input tag를 사용한다. -->
                                    	<input type="date" name="birth" class="form-control" id="birth"/>
                                        <form:label path="birth">BIRTH</form:label>
                                        <form:errors path="birth"></form:errors>
                                    </div>
                                    <!-- join btn -->
                                    <div class="d-flex justify-content-between form-floating mb-3">
                                        <button type="button" id="joinBackBtn" class="feature bg-danger bg-gradient text-white rounded-3">X</button>   
                                        <button type="button" id="joinBtn" class="feature bg-primary bg-gradient text-white rounded-3">O</button>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
    
    </main>
    <!-- Footer 적용 -->
     	<c:import url="../temp/footer.jsp"></c:import>
  	<!-- Footer 끝 -->
  	<script type="text/javascript" src="../js/joinFormCheck.js"></script>
</body>
</html>