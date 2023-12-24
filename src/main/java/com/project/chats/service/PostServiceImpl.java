package com.project.chats.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.chats.dao.PostDao;
import com.project.chats.entity.Post;

@Service
public class PostServiceImpl implements PostService {
	
	private final PostDao dao;
	
	//デフォルトコンストラクタ
	@Autowired PostServiceImpl(PostDao dao){
		this.dao = dao;
	}

	@Override
	public void save(Post post) {
		dao.insertPost(post);

	}

	@Override
	public List<Post> getAll() {
		return dao.getAll();
	}
	
	@Override
	public List<Post> getListByThread(int threadId){
		return dao.getListByThread(threadId);
	}
	
	@Override
	public void update(Post post) {
		dao.updatePost(post);

	}
	
	@Override
	public void delete(int id) {
		dao.deletePost(id);
	}
	
	@Override
	public Optional<Post> getPost(int id) {

		//Optional<Task>一件を取得 idが無ければ例外発生　
		try {
			return dao.findById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ThreadNotFoundException("指定されたタスクが存在しません");
		}
	}
	

}
