package com.memo.post.bo;

import java.util.List;

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
	
	public List<Post> getPostListByUserId(int userId){
		return postDAO.selectPostListByUserId(userId);
	}

}
