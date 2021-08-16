<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="d-flex p-4">

	<div class="logo">
		<h1 class="text-white font-weight-bold">메모게시판</h1>
	</div>
	
	<c:if test="${userName ne null}">
		<div class="login-info d-flex justify-content-end mr-3">
			<div class="mt-3">
				<span class="text-white"><b>${userName}</b> 님 안녕하세요. 
				</span><a href="/user/sign_out" class="text-white font-weight-bold">로그아웃</a>
			</div>
		</div>
	</c:if>
</div>