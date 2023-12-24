package com.project.chats.app.thread;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


/*
 * フォームクラス
 * */
public class ThreadForm {
	
	/*---------------------------------------*
	 * Property
	 * ---------------------------------------*/
    @Digits(integer = 1, fraction = 0)
    private int id;
	
	private int user_id;
	
    @Size(min=1, max=20)
	private String name;
	
    @NotEmpty
	private String explain;
    
    private boolean newThread;
	
	/*---------------------------------------*
	 * Constructor
	 * ---------------------------------------*/
	public ThreadForm() {
		
	}
	
	/*---------------------------------------*
	 * ＧｅｔｔｅｒとＳｅｔｔｅｒ
	 * ---------------------------------------*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public boolean isNewThread() {
		return newThread;
	}

	public void setNewThread(boolean newThread) {
		this.newThread = newThread;
	}
	
	

}
