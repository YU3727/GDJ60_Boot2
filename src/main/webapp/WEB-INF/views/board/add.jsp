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
                            <h1 class="fw-bolder">Get in touch</h1>
                            <p class="lead fw-normal text-muted mb-0">We'd love to hear from you</p>
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
                                <%-- <form id="contactForm" action="./add" method="post" enctype="multipart/form-data"> --%>
                                <!-- 검증을 위한 Spring Form / 위 form은 일반 html form이다 -->
                                <form:form id="contactForm" action="./add" method="post" modelAttribute="boardVO" enctype="multipart/form-data">
                                    <!-- title input(Spring Form)-->
                                    <div class="form-floating mb-3">
                                        <!-- html에서 type 속성이던게 태그명으로 바뀜, modelAttribute로 받는 VO의 setter 이름과 일치시켜야함(멤버변수명) = input tag의 name과 같은역할 -->
                                        <!-- parameter를 noticeVO 자체에 담아서 보냄 -->
                                        <form:input path="title" id="title" cssClass="form-control"/>
                                        <label for="title">Title</label>
                                        <form:errors path="title" cssStyle="color:red"></form:errors>
                                    </div>
                                    <!-- writer input-->
                                    <div class="form-floating mb-3">
                                        <form:input path="writer" id="writer" cssClass="form-control"/>
                                        <label for="writer">Writer</label>
                                        <form:errors path="writer"></form:errors>
                                    </div>
                                    
                                    <!-- 이게 될까? 시리즈 -->
                                    <!-- subName input, 이게 될까?-> Spring Form을 사용하면 된다... -->
                                    <div class="form-floating mb-3">
                                        <form:input path="subVO.subName" id="subVO.subName" cssClass="form-control"/>
                                        <label for="subVO.subName">Sub Name</label>
                                    </div>
                                    
                                    <!-- checkbox 등의 옵션을 받는 경우(동일한 이름으로 여러 파라미터 받기) -->
                                    <div class="form-floating mb-3">
                                        <form:input path="names" id="names" cssClass="form-control"/>
                                        <label for="names">names</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <form:input path="names" id="names" cssClass="form-control"/>
                                        <label for="names">names</label>
                                    </div>
                                    
                                    <!-- 파일 여러개 중 파일 하나의 이름 출력. List에는 boardFileVO의 getter가 없어서 안됨 -->
                                    <%-- <div class="form-floating mb-3">
                                        <form:input path="boardFileVOs.boardFileVO.fileName" id="names" cssClass="form-control"/>
                                        <label for="names">BoardFileName</label>
                                    </div> --%>
                                    
                                    <!-- 여려개 파일 중 index로 하나를 꺼내는건 될까? -> 안됨 -->
                                    <%-- <div class="form-floating mb-3">
                                        <form:input path="boardFileVOs.get(0).fileName" id="names" cssClass="form-control"/>
                                        <label for="names">BoardFileName</label>
                                    </div> --%>
                                    
                                    <!-- 배열처럼 표기하면 될까? 가능. 동적으로 만들고싶으면 c:forEach 쓰고 var로 순서맞추면 될거같음 -->
                                    <!-- index 번호에 빈것들은 null이 들어간다.(0부터 시작) 주의 // 파일이 있는거만 꺼내고 싶으면 if문으로 fileVO가 있는거만 꺼내오는 식으로 한다 -->
                                    <!-- 같은 index 번호를 넣으면 fileName이 여러개가 들어간다. -->
                                    <div class="form-floating mb-3">
                                        <form:input path="boardFileVOs[0].fileName" id="filenames" cssClass="form-control"/>
                                        <label for="filenames">BoardFileName</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <form:input path="boardFileVOs[1].fileName" id="filenames" cssClass="form-control"/>
                                        <label for="filenames">BoardFileName</label>
                                    </div>
                                    
                                    <!-- Contents input-->
                                    <div class="form-floating mb-3">
                                        <textarea class="form-control" id="contents" placeholder="Enter your message here..." style="height: 10rem" data-sb-validations="required" name="contents"></textarea>
                                        <label for="contents">Contents</label>
                                        <div class="invalid-feedback" data-sb-feedback="message:required">A message is required.</div>
                                    </div>

                                    <!-- 첨부파일 -->
                                    <div class="form-floating mb-3">
                                        <input type="file" name="boardFiles">
                                    </div>

                                    <!-- Submit Button-->
                                    <div class="d-grid"><button class="btn btn-primary btn-lg" id="submitButton" type="submit">Submit</button></div>
                                </form:form>
                                <%-- </form> --%>
                            </div>
                        </div>
                    </div>
                    <!-- Contact cards-->
                    <div class="row gx-5 row-cols-2 row-cols-lg-4 py-5">
                        <div class="col">
                            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-chat-dots"></i></div>
                            <div class="h5 mb-2">Chat with us</div>
                            <p class="text-muted mb-0">Chat live with one of our support specialists.</p>
                        </div>
                        <div class="col">
                            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-people"></i></div>
                            <div class="h5">Ask the community</div>
                            <p class="text-muted mb-0">Explore our community forums and communicate with other users.</p>
                        </div>
                        <div class="col">
                            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-question-circle"></i></div>
                            <div class="h5">Support center</div>
                            <p class="text-muted mb-0">Browse FAQ's and support articles to find solutions.</p>
                        </div>
                        <div class="col">
                            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-telephone"></i></div>
                            <div class="h5">Call us</div>
                            <p class="text-muted mb-0">Call us during normal business hours at (555) 892-9403.</p>
                        </div>
                    </div>
                </div>
            </section>
    
    </main>
    <!-- Footer 적용 -->
     	<c:import url="../temp/footer.jsp"></c:import>
  	<!-- Footer 끝 -->
  	<!-- url이 /notice/add.jsp 이므로.. -->
  	<!-- <script type="text/javascript" src="../js/boardValidation.js"></script> -->
</body>
</html>