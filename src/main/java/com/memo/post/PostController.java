package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;	
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;



@RequestMapping("post")
@Controller
public class PostController {
	@Autowired
	private PostBO postBO;
	
	@RequestMapping("/post_list_view")
	public String postListView(Model model,HttpServletRequest request) {
		
		HttpSession session=request.getSession();
		Integer userId=(Integer)session.getAttribute("userId");
		
		if(userId==null) {
			//세션정보에 로그인 정보가 없다면 user페이지로 
			return "redirect:/user/sign_in_view";
		}
		
		if(userId !=null) {
			List<Post> postList=postBO.getPostListByUserId(userId);
			model.addAttribute("postList", postList);
			model.addAttribute("listSize",postList.size());
		}
		
		
		model.addAttribute("viewName", "post/post_list");
		return"template/layout";
	}
	
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/post_create");
		return "template/layout";
	}
}
