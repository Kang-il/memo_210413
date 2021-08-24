package com.memo.post.bo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	@Autowired
	private PostDAO postDAO;
	@Autowired
	private FileManagerService fileManagerService;
	
	private static final int POST_MAX_SIZE=3;
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public List<Post> getPostListByUserId(int userId,Integer prevId ,Integer nextId){
		//1> 다음 : 가장 작은 수 ( 오른쪽 값 ) => nextIdParam 쿼리 : nextIdParam 보다 작은 3개를 가져온다 (Limit)
		//2> 이전 : 가장 큰 수 (왼쪽 값 )=>prevIdParam 쿼리 : 
			      //prevIdParam 보다 큰 3개를 가져온다 (Limit) 순서가 뒤집히므로 코드에서 정렬을 뒤집는다.
		String direction = null;
		Integer standardId = null;
		if(prevId!=null) {//이전 버튼 클릭 시
			direction="prev";
			standardId=prevId;
			
			//TODO :: 정렬을 뒤집어야 함
			List<Post> postList=postDAO.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
			Collections.reverse(postList);
			
			return postList;
			
		}else if(nextId != null) {
			direction="next";
			standardId=nextId;
		}
		
		return postDAO.selectPostListByUserId(userId,direction,standardId,POST_MAX_SIZE);
	}
	
	public Post getPostByPostIdAndUserId(int postId,int userId) {
		return postDAO.selectPostByPostIdAndUserId(postId,userId);
	}
	
	//가장 오른쪽 페이지 인가?
	public boolean isLastPage(int userId, int nextId) {
		// 게시글 번호 10 9 8 | 7 6 5 | 4 3 2 | 1
		// post
		return nextId == postDAO.selectPostIdByUserIdAndSort(userId,"ASC");
	}
	
	//가장 왼쪽 페이지 인가?
	public boolean isFirstPage(int userId, int prevId) {
		//10 일 때 가장 처음 페이지이다.
		return prevId == postDAO.selectPostIdByUserIdAndSort(userId,"DESC");
	}
	
	public int createPost(int userId, String subject, String content,String userLoginId, MultipartFile file){
		
		//filed업로드 후  image URL을 반환받아 DB에 넣을 인자값으로 구성.
		String imageUrl= generateImageUrlByFile(userLoginId, file);
		
		return postDAO.insertPost(userId, subject, content, imageUrl);
		
	}
	public int updatePost(int postId,int userId,String userLoginId,String subject,String content,MultipartFile file) {
		
		String imageUrl= generateImageUrlByFile(userLoginId, file);
		if(imageUrl != null) {
			Post post = postDAO.selectPostByPostIdAndUserId(postId, userId); //기존 imagepath가져옴
			String oldImageUrl=post.getImagePath();
			if(oldImageUrl!=null) {
				try {
					fileManagerService.deleteFile(oldImageUrl);
				}catch(IOException e) {
					logger.error(":::::::::::::::[파일 삭제] 삭제 중 에러 : "+e.getMessage()+" "+postId+" "+oldImageUrl);
				}
			}
		}
		
		return postDAO.updatePost(postId,userId,subject,content,imageUrl);
	}
	public void deletePost(int postId,int userId) {
		//유저 아이디와 포스트아이디가 일치하는 포스트 DB에서 가져오기
		Post post= postDAO.selectPostByPostIdAndUserId(postId, userId);
		if(post != null) {
			//포스트 지우기
			postDAO.deletePost(postId);
			//지우기 후 파일지우기
			if(post.getImagePath()!=null) {
				try {
					fileManagerService.deleteFile(post.getImagePath());
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
			
		}
	}

	private String generateImageUrlByFile(String userLoginId, MultipartFile file) {
		String imageUrl =null;
		if(file!=null) {// file이 null이 아니면 파일을 업로드 하고 이미지 URL을 만들겠다
			try {
				//컴퓨터에(서버에) 파일 업로드 후 DB에 저장해 나중에 웹에서 접근할 수 있는 Image URL을 얻어낸다
				imageUrl=fileManagerService.saveFile(userLoginId, file);
				logger.debug("[이미지 업로드 패스]::::::::::::"+imageUrl);
			}catch(IOException e) {
				//대략적인 이유가 나오는 메시지
				logger.error("[오류] 이미지 업로드 오류 ::::::::::::"+e.getMessage());
			}
		}
		return imageUrl;
	}
	
}
