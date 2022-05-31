package com.antra.antraaccountmanagementapi.security.factory;

import com.antra.antraaccountmanagementapi.model.JwtUser;
import com.antra.antraaccountmanagementapi.model.User;
import com.antra.antraaccountmanagementapi.model.UserRole;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUserFactory {

    private JwtUserFactory() {

    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getUid(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getUserRoleSet())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Collection<UserRole> userRoles) {
        return userRoles.stream()
            // todo: userRole.getRole().getRoleName() -> userRole.getRoleName(), due to the type change
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
                .collect(Collectors.toList());
    }
}
