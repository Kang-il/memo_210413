<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="d-flex justify-content-center">
    <div class="col-12">
		<h1 class="text-center">글 목록</h1>
		<table class="table table-hover text-center">
			<thead>
				<tr>
					<th>No.</th>
					<th>제목</th>
					<th>작성날짜</th>
					<th>수정날짜</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${listSize eq 0}">
				
				</c:if>
				<c:if test="${listSize ne 0}">
					<c:forEach var="post" items="${postList}">
						<tr>
							<td>${post.id}</td>
							<td><a href="/post/post_detail_view?postId=${post.id}">${post.subject}</a></td>
							<td>
								<fmt:formatDate value="${post.createdAt}" pattern="yyyy년 MM월 dd일 hh:mm:ss"/>
							</td>
							<td>
								<fmt:formatDate value="${post.updatedAt}" pattern="yyyy년 MM월 dd일 hh:mm:ss"/>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>    
    </div>
</div>
		<div class="d-flex justify-content-end my-3">
			<a href="/post/post_create_view" class="btn btn-primary">글쓰기</a>
		</div>