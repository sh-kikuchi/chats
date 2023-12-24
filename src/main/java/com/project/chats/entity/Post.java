package com.project.chats.entity;

import java.time.LocalDateTime;

public class Post {
	//プロパティ＝カラム
	private int id;
	private int thread_id;
	private int user_id;
	private String title;
	private String description;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;

	
	//コンストラクタ
	public Post() {
	}


	//ゲッター・セッター
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getThread_id() {
		return thread_id;
	}


	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getCreated_at() {
		return created_at;
	}


	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}


	public LocalDateTime getUpdated_at() {
		return updated_at;
	}


	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	

	

}
