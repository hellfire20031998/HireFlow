package com.hellFire.AuthService.services;

import com.hellFire.AuthService.exceptions.UserNotFoundException;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.respositories.IUserRepository;
import com.hellFire.AuthService.utils.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        return new CustomUserDetails(user);
    }
}
