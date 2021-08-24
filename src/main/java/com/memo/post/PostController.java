package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;	
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;



@RequestMapping("post")
@Controller
public class PostController {
	@Autowired
	private PostBO postBO;
	
	@RequestMapping("/post_list_view")
	public String postListView(Model model
			,HttpServletRequest request
			,@RequestParam(value="prevId" , required=false) Integer prevIdParam
			,@RequestParam(value="nextId" , required=false) Integer nextIdParam ) {
		
		HttpSession session=request.getSession();
		Integer userId=(Integer)session.getAttribute("userId");
		
		if(userId==null) {
			//세션정보에 로그인 정보가 없다면 user페이지로 
			return "redirect:/user/sign_in_view";
		}
		
			List<Post> postList=postBO.getPostListByUserId(userId,prevIdParam,nextIdParam);
			model.addAttribute("postList", postList);
			model.addAttribute("listEmpty",postList.isEmpty());
			
			int prevId=0;
			int nextId=0;
			//postList가 비어있지 않을경우 값 세팅
			if(postList.isEmpty()==false) {
				prevId= postList.get(0).getId();
				nextId= postList.get(postList.size()-1).getId();
				
				if(postBO.isFirstPage(userId, prevId)) {
					prevId=0;
				}
				
				if(postBO.isLastPage(userId, nextId)) {
					nextId=0;
				}
				
			}
			
		//게시글 번호 
		// 10 9 8
		// 7  6 5
		// 4  3 2
		// 1
		//1> 다음 : 가장 작은 수 ( 오른쪽 값 ) => nextIdParam 쿼리 : nextIdParam 보다 작은 3개를 가져온다 (Limit)
		//2> 이전 : 가장 큰 수 (왼쪽 값 )=>prevIdParam 쿼리 : prevIdParam 보다 큰 3개를 가져온다 (Limit) 순서가 뒤집히므로 코드에서 정렬을 뒤집는다.
		
		
		model.addAttribute("viewName", "post/post_list");
		model.addAttribute("prevId",prevId); //리스트 중 가장 앞쪽 제일 큰 id
		model.addAttribute("nextId",nextId); //리스트 중 가장 뒷쪽 제일 작은 id
		return"template/layout";
	}
	
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/post_create");
		return "template/layout";
	}
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(Model model, @RequestParam("postId") int postId, HttpServletRequest request) {
		HttpSession session=request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			//세션에 로그인 아이디가 없다면 로그인이 안 된것이므로 로그인 페이지로 리다이렉트
			return "redirect:/user/sign_in_view";
		}
		Post post = postBO.getPostByPostIdAndUserId(postId, userId);
		
		model.addAttribute("viewName", "post/post_detail");
		model.addAttribute("post", post);
		return "template/layout";

	}
}
