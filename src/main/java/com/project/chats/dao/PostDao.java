package com.project.chats.dao;
import java.util.List;
import java.util.Optional;

import com.project.chats.entity.Post;

public interface PostDao {
	
	void insertPost(Post post);
	void updatePost(Post post);
	int deletePost(int id);
	
	List<Post> getAll();
	
	List<Post> getListByThread(int threadId);
	
	
	Optional<Post> findById(int id);  //更新用

}
