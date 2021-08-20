package com.memo.post.bo;

import java.io.IOException;
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
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public List<Post> getPostListByUserId(int userId){
		return postDAO.selectPostListByUserId(userId);
	}
	
	public int createPost(int userId, String subject, String content,String userLoginId, MultipartFile file){
		
		String imageUrl=null;
		//filed업로드 후  image URL을 반환받아 DB에 넣을 인자값으로 구성.
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
		logger.debug("###########"+userId+subject+content+userLoginId);
		return postDAO.insertPost(userId, subject, content, imageUrl);
		
	}
}
