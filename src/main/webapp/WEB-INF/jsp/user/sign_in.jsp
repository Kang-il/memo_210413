<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center align-items-center py-5">
	<div class="login-box">
		<h1 class="mb-4">로그인</h1>
		
		<%-- 키보드 Enter키로 로그인이 될 수 있도록 form 태그를 만들어준다.(submit 타입의 버튼이 동작됨) --%>
		<form id="loginForm" action="/user/sign_in" method="post">
			<div class="input-group mb-3">
				<%-- input-group-prepend: input box 앞에 ID 부분을 회색으로 붙인다. --%>
				<div class="input-group-prepend">
					<span class="input-group-text">ID</span>
				</div>
				<input type="text" class="form-control" id="loginId" name="loginId">
			</div>
	
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">PW</span>
				</div>
				<input type="password" class="form-control" id="password" name="password">
			</div>
			
			<%-- btn-block: 로그인 박스 영역에 버튼을 가득 채운다. submit으로 만들면 enter로 로그인 버튼이 먹힌다. --%>
			<input type="submit" class="btn btn-block btn-primary" value="로그인">
			<a class="btn btn-block btn-dark" href="/user/sign_up_view">회원가입</a>
		</form>
	</div>
</div>

<script>
	$(document).ready(function(){
		$('#loginForm').submit(function(e){
			e.preventDefault();
			
			//validation
			let loginId=$('input[name=loginId]').val().trim();
			if(loginId==''){
				alert('아이디를 입력 해 주세요');
				return;
			}
			
			let password=$('#password').val();
			if(password==''){
				alert('비밀번호를 입력 해 주세요');
				return;
			}
			
			//form 태그의 속성중 action을 바라보고 그에 대한 값을 가져온다.
			let url=$(this).attr('action');
			let params=$(this).serialize();
			
			$.post(url,params).done(function(data){//성공하면 해당 function을 실행하겠다.
				if(data.result=='success'){
					alert('로그인 성공');
					location.href='/post/post_list_view';
				}else{
					alert('로그인에 실패 했습니다. 다시 로그인 해주세요');
				}
			});
			
			
		});
	});
</script>