<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   

<div class="d-flex justify-content-center">
 	<div class="post-box mt-3">
 		<h1>글 상세/수정</h1>
 		<input type="text" name="subject" class="form-control mb-3" value="${post.subject}"/>
 		<textarea name="content" class="form-control" rows="15" cols="100">${post.content}</textarea>
 		
 		<%-- 이미지가 있을 때에만 이미지 영역 추가 --%>
 		<div class="my-3">
 			<c:if test="${post.imagePath ne null}">
 				<img src="${post.imagePath}" alt="업로드 된 이미지" width="300px"/>
 			</c:if>
 			<c:if test="${post.imagePath eq null}">
 				<img src="/static/images/no_image.png" alt="이미지가 없습니다." width="300px"/>
 			</c:if>
 		</div>
 		<div>
 			<input type="file" class="form-control" name="postImage">
 		</div>
 		<div class="mt-3 clearfix mb-3">
 			<button id="postDelBtn" name="image" class="btn btn-secondary float-left" data-post-id="${post.id}">삭제</button>
 			<div class="float-right">
	 			<a href="/post/post_list_view" class="btn btn-dark" >목록으로</a>
	 			<button id="saveBtn" class="btn btn-primary" data-post-id="${post.id}">수정</button>
 			</div>
 		</div>
 	</div>
</div>
<script>

$(document).ready(function(){
	$('#postDelBtn').on('click',function(){
		let postId=$(this).data('post-id');
		
		$.ajax({
			type:'post'
			,url:'/post/delete'
			,data:{'postId':postId}
			,success:function(data){
				if(data.result===true){
				alert("삭제가 완료되었습니다.")
				location.href='/post/post_list_view';
				}else{
					alert('삭제 실패');
					return;
				}
			}
			,error:function(e){
				alert(e);
			}
		});
	});
	$('#saveBtn').on('click',function(){
 			let subject= $('input[name=subject]').val().trim();

 			if(subject==''){
 				alert('제목을 입력해 주세요');
 				return;
 			}
 			
 			let content=$('textarea[name=content]').val();
 			
 			if(content==''){
 				alert('내용을 입력해 주세요');
 				return;
 			}
 			
 			//파일 업로드 된 경우에만 확장자 체크
 			let file=$('input[name=postImage]').val();
 			console.log(file);
 			
 			if(file!=''){
 				// C:\abadfs\]sdfae_jpg
 				let ext = file.split('.').pop().toLowerCase(); //.을 기준으로 나누고 확장자가 있는 마지막 배열칸을 가져오고 소문자로 모두 변경
 				if($.inArray(ext,['gif','jpg','jpeg','png']) == -1){
 					alert("gif jpg jpeg png 파일만 업로드 할 수 있습니다.");
 					$('input[name=postImage]').val('');
 					return;
 				}
 			}
 			
 			//validation 끝
 			//서버에 보내기
 			//form태그를 javascript 로 보낸다
 			
 			let formData=new FormData();
 			
 			formData.append("postId",$(this).data('post-id'));
 			formData.append("subject",subject);
 			formData.append("content",content);
 			formData.append("file",$('input[name=postImage]')[0].files[0]);
 			
 			$.ajax({
 				url:'/post/update'
 				,type:'POST'
 				,data:formData
 				,processData:false
 				,contentType:false
 				,enctype:'multipart/form-data'
 				,success:function(data){
 					alert('메모가 수정되었습니다.');
 					location.reload();
 				}
 				,error: function(e){
 					alert(e);
 				}
 				
 				});

	});
});


</script>

