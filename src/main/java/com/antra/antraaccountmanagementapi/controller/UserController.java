package com.antra.antraaccountmanagementapi.controller;

import com.antra.antraaccountmanagementapi.model.User;
import com.antra.antraaccountmanagementapi.model.request.UserRegisterRequest;
import com.antra.antraaccountmanagementapi.service.UserService;
import com.antra.antraaccountmanagementapi.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final AuthenticationManager authenticationManager;
	
	private final UserService userServiceImpl;
	
	private final JwtUtils jwtUtils;
	
	@Autowired
	public UserController(
		AuthenticationManager authenticationManager,
		UserService userServiceImpl,
		JwtUtils jwtUtils
	) {
		this.authenticationManager = authenticationManager;
		this.userServiceImpl = userServiceImpl;
		this.jwtUtils = jwtUtils;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(
		@RequestBody UserRegisterRequest userLoginRequest
	) {
		if (userServiceImpl.emailExist(userLoginRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("email exists");
		} else if (userServiceImpl.usernameExist(userLoginRequest.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("email exists");
		}
		
		User user = userServiceImpl.registerUser(userLoginRequest);
		if (user == null) {
			logger.info("user couldn't be safe, username: {}", userLoginRequest.getUsername());
			return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body("we couldn't registered");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body("account successfully created");
	}
}
