package com.project.chats.service;

import java.util.List;
import java.util.Optional;

import com.project.chats.entity.Post;

public interface PostService {
	void save(Post post);
	void update(Post post);
	void delete(int id);
	
	List<Post> getAll();
	
	List<Post> getListByThread(int threadId);
	

	
	Optional<Post> getPost(int id); //更新用
}
