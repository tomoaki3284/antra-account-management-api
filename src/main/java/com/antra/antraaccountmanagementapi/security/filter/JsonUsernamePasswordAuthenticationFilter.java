package com.antra.antraaccountmanagementapi.security.filter;

import com.antra.antraaccountmanagementapi.model.request.UserLoginRequest;
import com.antra.antraaccountmanagementapi.utils.GsonUtil;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUsernamePasswordAuthenticationFilter.class);
	
	private boolean postMethodOnly = true;
	
	public JsonUsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/api/user/login", "POST"));
	}
	
	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws AuthenticationException {
		if (postMethodOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		UserLoginRequest userLoginRequest = obtainUserRequestBody(request);
		String username = userLoginRequest.getUsername();
		String password = userLoginRequest.getPassword();
		logger.info("user login username is {}", username);
		if (username == null) {
			username = "";
		}
		if (password == null) {
			password = "";
		}
		
		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		this.setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}
	
	private UserLoginRequest obtainUserRequestBody(HttpServletRequest request) {
		Scanner s = null;
		try {
			s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
		} catch (IOException e) {
			logger.error("fail to load request body ", e);
		}
		
		String body = s.hasNext() ? s.next() : "";
		return (UserLoginRequest) GsonUtil.getObj().convertJsonToObject(body, UserLoginRequest.class);
	}
}
