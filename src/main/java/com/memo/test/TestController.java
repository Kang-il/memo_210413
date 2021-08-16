package com.memo.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.test.bo.TestBO;

@Controller
public class TestController {
	@Autowired
	private TestBO testBO;
	
	@RequestMapping("/test")
	@ResponseBody
	public String helloWorld() {
		return "helloWorld!";
	}
	@RequestMapping("/test_db")
	@ResponseBody
	public Map<String, Object> testDB(){
		Map<String,Object> result= testBO.gettestDB();
		return result; //jackson 라이브러리때문에 JSON으로 반환
	}
	
	@RequestMapping("/test_jsp")
	public String testJsp() {
		return "test/test";
	}
}
