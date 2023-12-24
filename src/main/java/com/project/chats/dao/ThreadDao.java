package com.project.chats.dao;
import java.util.List;
import java.util.Optional;

import com.project.chats.entity.Thread;

public interface ThreadDao {
	
	void insertThread(Thread thread);
	void updateThread(Thread thread);
	int deleteThread(int id);
	
	List<Thread> getAll();
	

	Optional<Thread> findById(int id);  //更新用
	
}
