package com.project.chats.service;

import java.util.List;
import java.util.Optional;

import com.project.chats.entity.Thread;

public interface ThreadService {
	
	void save(Thread thread);
	void update(Thread thread);
	void delete(int id);
	
	List<Thread> getAll(); //インデックス
	
	Optional<Thread> getThread(int id); //更新用
	

}
 