package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.UserDto;
import com.hellFire.AuthService.dto.requests.CreateUserRequest;
import com.hellFire.AuthService.mapper.IUserMapper;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.respositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IUserMapper userMapper;

    public String login(String username, String password) {

        User user = userRepository.findByUsernameAndDeleted(username, false);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(username);
    }

    public UserDto register(CreateUserRequest request) {
        User user = userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (user != null) {
            throw new RuntimeException("User already exists");
        }
        user = userMapper.toEntity(request);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}