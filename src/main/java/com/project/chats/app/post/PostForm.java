package com.project.chats.app.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PostForm {
	//プロパティ
	private int id;
	private int thread_id;
	private int user_id;
	
    @Size(min=1, max=20)
	private String title;
	
    @NotEmpty
	private String description;
    
    private boolean newPost;
    

	//　コンストラクタ
	public PostForm() {
		
	}

	//　ゲッター/セッター
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

	public boolean isNewPost() {
		return newPost;
	}

	public void setNewPost(boolean newPost) {
		this.newPost = newPost;
	}
	

	
}
