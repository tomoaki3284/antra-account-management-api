package com.antra.antraaccountmanagementapi.security.service;

import com.antra.antraaccountmanagementapi.model.JwtUser;
import com.antra.antraaccountmanagementapi.model.User;
import com.antra.antraaccountmanagementapi.repository.UserRepository;
import com.antra.antraaccountmanagementapi.security.factory.JwtUserFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            JwtUser jwtUser = JwtUserFactory.create(user);
            return jwtUser;
        }
    }
}
