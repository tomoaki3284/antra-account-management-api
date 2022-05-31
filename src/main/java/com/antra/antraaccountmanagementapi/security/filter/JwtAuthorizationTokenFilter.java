package com.antra.antraaccountmanagementapi.security.filter;

import com.antra.antraaccountmanagementapi.utils.JwtUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

//client => request endpoint =>
//  server => filter analyze header(JWT) fail => reject 403 / success => check role in endpoint
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtil;
    private final String tokenHeader;

    private final Logger logger = LogManager.getLogger(this.getClass());

    public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, JwtUtils jwtUtil, String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(
		HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String requestHeader = httpServletRequest.getHeader(tokenHeader);


        String username = null;
        String authToken = null;
        if(requestHeader != null && requestHeader.contains("Bearer ")) {
            authToken = requestHeader.substring(7).trim();
            logger.debug("user login jwt is {}", authToken);
            try {
                username = jwtUtil.getUserNameFromJwtToken(authToken);
                logger.debug("get username from jwt token {}", username);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (Exception e) {
                logger.warn("the token is expired and not valid anymore", e);
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            logger.debug("get jwt userdetails {}", userDetails);
            if (jwtUtil.validateJwtToken(authToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
