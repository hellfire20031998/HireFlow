package com.hellFire.AuthService.services;

import com.hellFire.AuthService.exceptions.UserNotFoundException;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.model.UserRole;
import com.hellFire.AuthService.respositories.IUserRepository;
import com.hellFire.AuthService.respositories.IUserRoleRepository;
import com.hellFire.AuthService.utils.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final UserRoleService userRoleService;

    public CustomUserDetailsService(IUserRepository userRepository,
                                    UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        List<UserRole> userRoles = userRoleService.getUserRoles(user.getId());
        return new CustomUserDetails(user, userRoles);
    }
}
