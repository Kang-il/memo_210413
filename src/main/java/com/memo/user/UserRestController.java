package com.memo.user;


import java.util.HashMap;
import java.util.Map;

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
}
