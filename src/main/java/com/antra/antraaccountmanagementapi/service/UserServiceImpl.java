package com.antra.antraaccountmanagementapi.service;

import com.antra.antraaccountmanagementapi.model.User;
import com.antra.antraaccountmanagementapi.model.request.UserRegisterRequest;
import com.antra.antraaccountmanagementapi.repository.UserRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User registerUser(UserRegisterRequest userRegisterRequest) {
		User user = new User(
			userRegisterRequest.getUsername(),
			userRegisterRequest.getPassword(),
			userRegisterRequest.getEmail(),
			LocalDate.now()
		);
		
		return userRepository.save(user);
	}
	
	@Override
	public boolean usernameExist(String username) {
		return false;
	}
	
	@Override
	public boolean emailExist(String email) {
		return false;
	}
}
