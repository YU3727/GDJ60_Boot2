<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container px-5">
        <a class="navbar-brand" href="/">Goodee</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="/">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="/notice/list">Notice</a></li>
                <li class="nav-item"><a class="nav-link" href="/qna/list">Q&A</a></li>
                <li class="nav-item"><a class="nav-link" href="/member/admin">Admin</a></li>

                <!-- <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdownBlog" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Blog</a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownBlog">
                        <li><a class="dropdown-item" href="blog-home.html">Blog Home</a></li>
                        <li><a class="dropdown-item" href="blog-post.html">Blog Post</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdownPortfolio" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Portfolio</a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownPortfolio">
                        <li><a class="dropdown-item" href="portfolio-overview.html">Portfolio Overview</a></li>
                        <li><a class="dropdown-item" href="portfolio-item.html">Portfolio Item</a></li>
                    </ul>
                </li> -->
                	<!-- pom.xml에 security library 추가하고, 로그인 했을때/안했을때 구분을 다시 넣어준다. -->
                	<sec:authorize access="isAuthenticated()">
		                <li class="nav-item"><a class="nav-link" href="/member/logout">Logout</a></li>
                		<li class="nav-item"><a class="nav-link" href="/member/mypage">My Page</a></li>
               		</sec:authorize>
					<sec:authorize access="!isAuthenticated()">
		                <li class="nav-item"><a class="nav-link" href="/member/login">Login</a></li>
		                <li class="nav-item"><a class="nav-link" href="/member/join">Join</a></li>
					</sec:authorize>
                <!-- 이동위치가 루트(/)가 아니라 현재위치에서 바뀌게는 어떻게 할 까? -->
                <li class="nav-item"><a class="nav-link" href="/?lang_opt=ko">KR</a></li>
		        <li class="nav-item"><a class="nav-link" href="/?lang_opt=en">EN</a></li>
            </ul>
        </div>
    </div>
</nav>