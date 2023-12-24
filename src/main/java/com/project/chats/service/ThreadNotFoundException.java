package com.project.chats.service;

public class ThreadNotFoundException extends RuntimeException  {
	private static final long serialVersionUID = 1L;

	public ThreadNotFoundException(String message) {
		super(message);
	}

}
