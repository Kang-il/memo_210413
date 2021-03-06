package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {
	@Autowired
	private PostBO postBO;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/create")
	public Map<String,String> postCreate(
			@RequestParam("subject") String subject
		   ,@RequestParam("content") String content
		   ,@RequestParam(value="file" , required = false) MultipartFile file
		   ,HttpServletRequest request){
		
		Map<String, String> result=new HashMap<>();
		HttpSession session=request.getSession();
		int userId = (int)session.getAttribute("userId");
		String userLoginId=(String)session.getAttribute("userLoginId");
		
		logger.debug("파일 :::::" + subject);
		logger.debug("파일 :::::" + content);
		//logger.debug("파일 :::::" + file.getName());
		
		
		int row=postBO.createPost(userId, subject, content, userLoginId, file);
		if(row>0) {
			result.put("result", "success");
		}else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
	@PostMapping("/update")
	public Map<String,String> postUpdate(
				@RequestParam("postId") int postId
	   		   ,@RequestParam("subject") String subject
			   ,@RequestParam("content") String content
			   ,@RequestParam(value="file" , required = false) MultipartFile file
			   ,HttpServletRequest request){
		
		HttpSession session = request.getSession();
		
		Integer userId =(Integer) session.getAttribute("userId");
		String userLoginId=(String) session.getAttribute("userLoginId");
		Map<String,String> result = new HashMap<>();
		
		int row =postBO.updatePost(postId,userId,userLoginId,subject,content,file);
		if(row>0) {
			result.put("result", "success");
		}else {
			result.put("result", "fail");
		}
		
		return result;
		
	}
	@PostMapping("/delete")
	public Map<String,Boolean> postDelete(@RequestParam("postId") int postId,HttpServletRequest request){
		HttpSession session=request.getSession();
		
		Map<String,Boolean> result=new HashMap<>();
		
		Integer userId=(Integer)session.getAttribute("userId");
		if(userId!=null) {
			postBO.deletePost(postId,userId);
			result.put("result",true);
		}else {
			result.put("result", false);
		}
		
		
		return result;
	}
}
