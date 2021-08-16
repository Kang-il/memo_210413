package com.memo.user;	

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;




/**
 * 화면만 구성하는 컨트롤러
 * @author h5tchi
 */
@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 회원가입 화면
	 * @param model
	 * @return
	 */
	@RequestMapping("/sign_up_view")
	public String signUpView(Model model) {
		Date date=new Date();
		model.addAttribute("currentTime", date.getTime());
		model.addAttribute("viewName","user/sign_up");
		return "template/layout";
	}
	
	@RequestMapping("/sign_in_view")
	public String signInView(Model model) {
		Date date=new Date();
		model.addAttribute("currentTime", date.getTime());
		model.addAttribute("viewName","user/sign_in");
		return "template/layout";
	}
	
	/**
	 * form 태그를 이용한 submit -None Ajax
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign_up_for_submit")
	public String signUpForSubmit(
			@RequestParam("loginId")String loginId
			,@RequestParam("password")String password
			,@RequestParam("name")String name
			,@RequestParam("email")String email
			) {
		
		//1. 비밀번호 암호화!(MD5 --해시 단방향)
		String encryptPassword = EncryptUtils.md5(password);
		
		//2. DBInsert
		userBO.addUser(loginId, encryptPassword, name, email);
		
		
		//3. redirect @ResponseBody 가 아닌 일반 @Controller 에서 작동
		return"redirect:/user/sign_in_view";
	}
	@RequestMapping("/sign_out")
	public String signOut(HttpServletRequest request) {
		HttpSession session=request.getSession();
//		
//		session.removeAttribute("userLoginId");
//		session.removeAttribute("userName");
//		session.removeAttribute("userId");
		
		session.invalidate();

		
		return"redirect:/user/sign_in_view";
	}
	
	
}
