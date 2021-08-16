<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="sign-up-form">

	<h3 class="mt-3 font-weight-bold">회원가입</h3>
	
	<form id="signUpForm" method="post" action="/user/sign_up_for_submit">
	
		<table class="table mt-3">
			<tbody>
				<tr>
					<th class="bg-light">*아이디</th>
					<td>
						<div class="d-flex justify-content-between align-items-center">
							<input type="text" class="form-control col-8" id="loginId" name="loginId"/> 
							<button  id="loginIdCheckBtn" class="btn btn-success is-duplication-btn ml-3">중복확인</button> 
						</div>
						<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해 주세요.</div>
						<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
						<div id="idCheckOk" class="small text-success d-none">사용가능한 ID입니다.</div>
					</td>
					
				</tr>
				<tr>
					<th class="bg-light">*비밀번호</th>
					<td><input type="password" class="form-control" id="password" name="password"></td>
				</tr>
				<tr>
					<th class="bg-light">*비밀번호 확인</th>
					<td><input type="password" class="form-control" id="checkPassword"></td>
				</tr>
				<tr>
					<th class="bg-light">*이름</th>
					<td><input type="text" class="form-control" id="name" name="name"></td>
				</tr>
				<tr>
					<th class="bg-light">*이메일</th>
					<td><input type="text" class="form-control" id="email" name="email"></td>
				</tr>
			</tbody>
		</table>
	
		<div class="d-flex justify-content-end">
			<button type="submit" id="signUpBtn" class="btn btn-info mb-3 p-1">회원가입</button>
		</div>
		
	</form>
	
</div>

<script>
	$(document).ready(()=>{
		//중복확인
		$('#loginIdCheckBtn').on('click',(e)=>{
			e.preventDefault();
			let loginId=$('#loginId').val().trim();

				//idCheckLength
				//idCheckDuplicated
				//idChekOk
		
			//ID 4자가 아니면 경고문구 노출
			if(loginId.length<4){
				$('#idCheckLength').removeClass('d-none');
				$('#idCheckDuplicated').addClass('d-none');
				$('#idCheckOk').addClass('d-none');
				return;
			}
				
			//AJAX 통신으로 중복여부 확인 후 동적으로 문구 노출
			$.ajax({
				url:'/user/is_duplicated_id'
				,type:'get'
				,data:{'loginId':loginId}
				,success:(data)=>{
					if(data.result===true){//중복인 경우
						$('#idCheckLength').addClass('d-none');
						$('#idCheckDuplicated').removeClass('d-none');
						$('#idCheckOk').addClass('d-none');
						
					}else{//중복이 아닌경우
						$('#idCheckOk').removeClass('d-none');
						$('#idCheckLength').addClass('d-none');
						$('#idCheckDuplicated').addClass('d-none');
					}
				}
				,error:(error)=>{
					alert('아이디 중복 확인이 실패했습니다 관리자에게 문의해 주세요');
				}
			});
			
		});
		
		//회원가입 버튼 동작
		$('#signUpForm').submit((e)=>{
			e.preventDefault();
			
			//submit 의 기본동작을 중단시킨다.
			//validation
			let loginId=$('#loginId').val().trim();
			if(loginId==''){
				alert('아이디를 입력하세요');
				return;
			}
			
			let password=$('#password').val()
			let checkPassword=$('#checkPassword').val()
			
			if(password!=checkPassword || password=='' || checkPassword==''){
				alert('비밀번호가 올바르지 않습니다.');
				$('#password').val('');
				$('#checkPassword').val('');
				return;
			}
			
			let name=$('#name').val().trim();
			if(name==''){
				alert('이름을 입력해 주세요.');
				return;
			}
			
			let email=$('#email').val().trim();
			if(email==''){
				alert('이메일을 입력해 주세요.');
				return;
			}
			if($('#idCheckOk').hasClass('d-none')){
				alert('중복체크를 확인하세요');
				return;
			}
			
			//서버로 보내는 방법
			//---1) submit ::name 속성에 있는 값들이 서버로 넘어간다. request param
			// $(this)[0].submit();
			
			//---2) ajax
			
				let url='/user/sign_up_for_ajax';
				let data = $('#signUpForm').serialize(); //폼태그 name input(request param) 이 구성된다.만약 이를 사용하지 않으면
				
	
				
				//data json을 임의로 구성해야 한다.
				$.post(url,data).done((data) => {//ajax 호출시 반환된 데이터
					if(data.result=="성공"){
						alert("회원가입을 환영합니다! \n 로그인을 해주세요!");
						location.href="/user/sign_in_view";
					}else{
						alert("가입 실패!");
					}
				});
				
		});
		
		$('#loginId').on('keyup',()=>{
			$('#idCheckLength').addClass('d-none');
			$('#idCheckDuplicated').addClass('d-none');
			$('#idCheckOk').addClass('d-none');
		});
		
	
		
		
		
		
	});
</script>