package com.project.chats.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.chats.dao.ThreadDao;
import com.project.chats.entity.Thread;

@Service
public class ThreadServiceImpl implements ThreadService {
	
	private final ThreadDao dao;
	
	//クラス名と同じのデフォルトコンストラクタを作成する
	@Autowired ThreadServiceImpl(ThreadDao dao){
		this.dao = dao;
	}

	@Override
	public void save(Thread thread) {
		dao.insertThread(thread);

	}

	@Override
	public void update(Thread thread) {
		dao.updateThread(thread);

	}
	
	@Override
	public void delete(int id) {
		dao.deleteThread(id);
	}
	
	@Override
	public List<Thread> getAll() {
		return dao.getAll();
	}
	
	

	
	@Override
	public Optional<Thread> getThread(int id) {

		//Optional<Task>一件を取得 idが無ければ例外発生　
		try {
			return dao.findById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ThreadNotFoundException("指定されたタスクが存在しません");
		}
	}
	


}
