package com.antra.antraaccountmanagementapi.model.request;

public class UserLoginRequest {
	
	private String password;
	private String username;
	
	public UserLoginRequest() {
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
