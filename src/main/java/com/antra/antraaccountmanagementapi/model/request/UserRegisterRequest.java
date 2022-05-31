package com.antra.antraaccountmanagementapi.model.request;

import com.antra.antraaccountmanagementapi.model.Role;
import java.util.List;

public class UserRegisterRequest {
	private String username;
	private String password;
	private String email;
	private List<Role> roles;
	
	public UserRegisterRequest(String username, String password, String email, List<Role> roles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}
	
	public UserRegisterRequest() {}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
