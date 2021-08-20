<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <div class="d-flex justify-content-center">
 	<div class="post-box">
	 		<h1 class="my-3">글쓰기</h1>
	 		<input type="text" class="form-control mb-2" name="subject" placeholder="제목을 입력해 주세요">
	 		<textarea class="form-control mb-3" rows="15" cols="100" name="content" placeholder="내용을 입력해 주세요"></textarea>
	 	<div class="mb-3">
	 		<%--multiple 여러 파일을 업로드 할 경우 추가할 프로퍼티 --%>
	 		<%--accept 특정 확장자 파일만 받는다. --%>
	 		<input type="file" class="form-control" name="postImage" accept=".jpg , .jpeg , .png , .gif">
	 	</div>
	 	<div class="mb-3 clearfix">
	 		<button type="button" class="btn btn-dark float-left" id="postListBtn">목록</button>
	 		<div class="float-right">
	 			<button type="reset" class="btn btn-secondary mr-2" id="clearBtn">모두 지우기</button>
	 			<button type="button" class="btn btn-info" id="saveBtn">올리기</button>
	 		</div>
	 	</div>
 	</div>
 	
 </div>
 <script>
 	$(document).ready(function(){
 		//콕록 버튼 클릭
 		$('#postListBtn').on('click',function(){
 			location.href="/post/post_list_view"
 		});
 		//모두지우기 버튼클릭
	 	$('#clearBtn').on('click',function(){
	 		$('input[name=subject]').val('');
	 		$('textarea[name=content]').val('');
	 		$('input[name=postImage]').val('');
	 	});
 		// 저장버튼
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
 			console.log("######"+content);
 			console.log("######"+subject);
 			//validation 끝
 			//서버에 보내기
 			//form태그를 javascript 로 보낸다
 			let formData=new FormData();
 			formData.append("subject",subject);
 			formData.append("content",content);
 			
 			//$('input[name=postImage]')[0] 첫 번째 인풋 파일 태그
 			//.files[0] 업로드 된 파일 중 첫 번째 의미
 			formData.append("file",$('input[name=postImage]')[0].files[0]);
 			//serialize 쿼리스트링으로 되어있음!! 
 			$.ajax({
 				type:"POST"
 				,url:"/post/create"
 				,data:formData
 			//필수 파라미터 :: 파일 업로드 시---------------
 				,processData:false 
 				//default ::true 로 넘어감 -- true일 경우  :: data JSON , QueryString -- String값으로 넘어감 
 				// false -- file 이 통으로 올라가야 하기 때문!!!!!!
 				,contentType:false
 				,enctype:'multipart/form-data'
 			//----------------------------------------
 				,success:function(data){
 					alert(data.result);
 					location.href='/post/post_list_view';
 				}
 				,error:function(e){
 					alert('메모저장 실패 관리자에게 문의 해 주세요');
 				}
 			});
 		});
 	});
 
 </script>