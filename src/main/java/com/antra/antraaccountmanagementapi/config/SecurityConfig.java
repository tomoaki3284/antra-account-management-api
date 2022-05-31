package com.antra.antraaccountmanagementapi.config;

import com.antra.antraaccountmanagementapi.model.JwtUser;
import com.antra.antraaccountmanagementapi.security.filter.JsonUsernamePasswordAuthenticationFilter;
import com.antra.antraaccountmanagementapi.security.JwtAuthenticationEntryPoint;
import com.antra.antraaccountmanagementapi.security.filter.JwtAuthorizationTokenFilter;
import com.antra.antraaccountmanagementapi.utils.GsonUtil;
import com.antra.antraaccountmanagementapi.utils.JwtUtils;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${jwt.header}")
	private String tokenHeader;
	
	private final JwtUtils jwtUtil;
	
	private final UserDetailsService jwtUserDetailsService;
	
	private final JwtAuthenticationEntryPoint jwtUnauthorizedHandler;
	
	@Autowired
	public SecurityConfig(JwtUtils jwtUtil, UserDetailsService jwtUserDetailsService, JwtAuthenticationEntryPoint jwtUnauthorizedHandler) {
		this.jwtUtil = jwtUtil;
		this.jwtUserDetailsService = jwtUserDetailsService;
		this.jwtUnauthorizedHandler = jwtUnauthorizedHandler;
	}
	
	@Autowired
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(jwtUserDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
		JsonUsernamePasswordAuthenticationFilter jwtFilter = new JsonUsernamePasswordAuthenticationFilter();
		jwtFilter.setAuthenticationManager(authenticationManagerBean());
		jwtFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		jwtFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		return jwtFilter;
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.cors().and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtUnauthorizedHandler)
			.and()
			.authorizeRequests()
			.antMatchers("/api/user/login", "/api/user/register").permitAll()
			.anyRequest().authenticated();
		
		JwtAuthorizationTokenFilter authorizationTokenFilter = new JwtAuthorizationTokenFilter(userDetailsService(), jwtUtil, tokenHeader);
		
		httpSecurity
			.addFilterAt(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(authorizationTokenFilter, JsonUsernamePasswordAuthenticationFilter.class);
		
		httpSecurity
			.headers()
			.frameOptions().sameOrigin()
			.cacheControl();
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return (httpServletRequest, httpServletResponse, authentication) -> {
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			PrintWriter writer = httpServletResponse.getWriter();
			final Map<String, String> responseToken = new HashMap<>();
			responseToken.put("loginResult", "success");
			responseToken.put("bearerToken", "Bearer " + jwtUtil.generateJwtToken((JwtUser)authentication.getPrincipal()));
			writer.write(GsonUtil.getObj().getGson().toJson(responseToken));
			writer.flush();
		};
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return (httpServletRequest, httpServletResponse, e) -> {
			httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			PrintWriter writer = httpServletResponse.getWriter();
			final Map<String, String> responseToken = new HashMap<>();
			
			responseToken.put("loginResult", "Fail");
			responseToken.put("error", "Incorrect username or password. ");
			writer.write(GsonUtil.getObj().getGson().toJson(responseToken));
			writer.flush();
		};
	}
}
