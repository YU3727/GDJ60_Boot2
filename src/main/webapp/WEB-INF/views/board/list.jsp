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
		
		<section class="bg-light py-5">
            <div class="container px-5 my-5">
                <div class="text-center mb-5">
                    <h1 class="fw-bolder">${board} List</h1>
                    <p class="lead fw-normal text-muted mb-0">April 18, 2023</p>
                </div>
                
                <div>
                	<table class="table table-hover">
                		<thead>
                			<tr>
                				<th>Num</th>
                				<th>Title</th>
                				<th>Writer</th>
                				<th>Date</th>
                				<th>Hit</th>
                			</tr>
                		</thead>
                		<tbody>
                		<c:forEach items="${list}" var="boardVO">
                			<tr>
								<td>${boardVO.num}</td>
								<td>${boardVO.title}</td>
								<td>${boardVO.writer}</td>
								<td>${boardVO.regDate}</td>
								<td>${boardVO.hit}</td>
                			</tr>
                		</c:forEach>
                		</tbody>
                	
                	</table>
                </div>
                
                
                <!-- 원시 pager -->
                <c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
                	<a>${i}</a>
                </c:forEach>
                
                <!-- pager -->
                <div class="d-flex justify-content-center">
					<nav aria-label="Page navigation example">
					  <ul class="pagination">
					  
					  	<li class="page-item">
					      <a class="page-link" href="./list?page=1&kind=${pager.kind}&search=${pager.search}" aria-label="Previous" data-board-page="1">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
					  
					    <li class="page-item ${pager.before ? 'disabled' :''}" data-board-page="${pager.startNum-1}">
					      <a class="page-link" href="./list?page=${pager.startNum-1}&kind=${pager.kind}&search=${pager.search}" aria-label="Previous">
					        <span aria-hidden="true">&lsaquo;</span>
					      </a>
					    </li>
					    
					    <c:forEach begin="${pager.startNum}" end="${pager.lastNum}" step="1" var="i">
					    	<li class="page-item"><a class="page-link" href="./list?page=${i}&kind=${pager.kind}&search=${pager.search}" data-board-page="${i}">${i}</a></li>			    
					    </c:forEach>
				
						<!-- 검색을 해서 나온 결과가 더 없으면 못누르게 해야함 -->
					    <li class="page-item ${pager.after ? 'disabled' :''}">
					      <a class="page-link" href="./list?page=${pager.lastNum+1}&kind=${pager.kind}&search=${pager.search}" aria-label="Next" data-board-page="${pager.lastNum+1}">
					        <span aria-hidden="true">&rsaquo;</span>
					      </a>
					    </li>
					    
					    <li class="page-item">
					      <a class="page-link" href="./list?page=${pager.totalPage}&kind=${pager.kind}&search=${pager.search}" aria-label="Next" data-board-page="${pager.totalPage}">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
					  </ul>
					</nav>
				</div>
                <!-- pager -->
                
                <!-- search -->
				<div class="d-flex justify-content-center">
					<form class="row g-3" action="./list" method="get" id="searchForm">
						<input type="hidden" name="page" value="1" id="page">
						<div class="col-3">
							<label for="kind" class="visually-hidden">Kind</label>
							<select class="form-select" name="kind" id="kind" aria-label="Default select example">
								<option value="title" ${pager.kind eq 'title'? 'selected':''}>제목</option>
								<option value="writer" ${pager.kind eq 'writer'? 'selected':''}>작성자</option>
								<option value="contents" ${pager.kind eq 'contents'? 'selected':''}>내용</option>
							</select>
						</div>
						<div class="col-5">
							<label for="search" class="visually-hidden">Search</label>
							<input type="text" class="form-control" name="search" value="${pager.search}" id="search" placeholder="검색어를 입력하세요">
						</div>
						<div class="col-4">
							<button type="submit" class="btn btn-primary mb-3">검색</button>
						</div>
					</form>	
				</div>
				<!-- search -->
                
            </div>
         </section>
    
    </main>
    <!-- Footer 적용 -->
     	<c:import url="../temp/footer.jsp"></c:import>
  	<!-- Footer 끝 -->
</body>
</html>