package com.memo.user;


import java.util.HashMap;		
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.model.User;
/**
 * 데이터만 처리하는 API용 Controller
 * @author h5tchi
 *
 */
@RestController
@RequestMapping("/user") //Controller + ResponseBody에 대한 Annotation
public class UserRestController {
	@Autowired
	private UserBO userBO;
	
	@RequestMapping("/is_duplicated_id")
	public Map<String,Boolean> isDuplicatedId(@RequestParam("loginId") String loginId){
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		User user=userBO.getUserByLoginId(loginId);
		if(user==null) {
			result.put("result", false);
		}else {
			result.put("result",true);
		}
		return result;
	}
	
	@PostMapping("/sign_up_for_ajax")
	public Map<String,String> signUpForAjax(
			@RequestParam("loginId")String loginId
			,@RequestParam("password")String password
			,@RequestParam("name")String name
			,@RequestParam("email")String email
			){
		System.out.println("::::::::::::"+loginId+" "+password+" "+name+" "+email);
		String encryptedPassword=EncryptUtils.md5(password);
		userBO.addUser(loginId, encryptedPassword, name, email);
		
		Map<String,String> result=new HashMap<>();
		
		result.put("result", "성공");
		return result;
	}
	
	@PostMapping("/sign_in")
	public Map<String,String> signIn( @RequestParam("loginId") String loginId
									 ,@RequestParam("password")String password
									 ,HttpServletRequest request){
		
		Map<String,String> result=new HashMap<>();
		//1. password MD5로 해싱한다.
			String encryptedPassword = EncryptUtils.md5(password);
			
		//2. loginId 와 password를 가져와 있으면 로그인 성공 -- DB연동하여 확인
			User user=userBO.getUserByLoginIdAndPassword(loginId, encryptedPassword);
		//3. 성공시 Session 에 저장하여 로그인 상태를 유지한다. 
			//쿠키와 세션
			
			if(user!=null) {
				HttpSession session=request.getSession();
				session.setAttribute("userLoginId",user.getLoginId());
				session.setAttribute("userName", user.getName());
				session.setAttribute("userId", user.getId());
				
				//성공시 보내줄 값 넣기
				result.put("result", "success");
		//4. 실패시 Session 저장하지 않고 fail result 반환	
			}else {
				//성공시 실패시 값 넣기
				result.put("result", "fail");
				result.put("message", "존재하지 않는 사용자 입니다.");
			}
		
		
		return result;
	}
	

	
}
