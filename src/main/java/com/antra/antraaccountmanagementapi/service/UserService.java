package com.antra.antraaccountmanagementapi.service;

import com.antra.antraaccountmanagementapi.model.User;
import com.antra.antraaccountmanagementapi.model.request.UserRegisterRequest;

public interface UserService {
	
	User registerUser(UserRegisterRequest userRegisterRequest);
	
	boolean usernameExist(String username);
	
	boolean emailExist(String email);
}
