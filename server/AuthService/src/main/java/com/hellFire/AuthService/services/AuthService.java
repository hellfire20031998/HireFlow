package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.UserDto;
import com.hellFire.AuthService.dto.requests.CreateUserRequest;
import com.hellFire.AuthService.dto.responses.UserResponse;
import com.hellFire.AuthService.exceptions.InvalidCredentialsException;
import com.hellFire.AuthService.exceptions.UserAlreadyExistsException;
import com.hellFire.AuthService.exceptions.UserNotFoundException;
import com.hellFire.AuthService.mapper.IUserMapper;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.model.UserRole;
import com.hellFire.AuthService.respositories.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final IUserMapper userMapper;
    private final UserRoleService userRoleService;
//    private final UserVerificationService userVerificationService;
//    private final EmailVerificationTokenService emailVerificationTokenService;

    public AuthService(
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            IUserMapper userMapper,
            UserRoleService userRoleService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;

    }

    @Transactional
    public UserResponse login(String username, String password) {

        User user = userRepository
                .findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        if (!user.isActive()) {
            throw new RuntimeException("User account is disabled");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

//        if (!userVerificationService.isEmailVerified(user)) {
//            throw new RuntimeException("Email not verified");
//        }

        List<UserRole> userRoleList = userRoleService.getUserRoles(user.getId());
        List<String> roles = userRoleList.stream().map(userRole -> userRole.getRole().getName()).toList();

        return new UserResponse(jwtService.generateToken(user.getUsername(), userRoleList),
                    userMapper.toDto(user),
                        roles
                    );
    }

    @Transactional
    public UserResponse register(CreateUserRequest request) {

        boolean exists = userRepository
                .existsByUsernameOrEmail(request.getUsername(), request.getEmail());

        if (exists) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

//        // ✅ Create verification row
//        userVerificationService.createForUser(user);
//
//        // ✅ Create email verification token
//        emailVerificationTokenService.createAndSend(user);

        List<UserRole> userRoleList = userRoleService.getUserRoles(user.getId());
        List<String> roles = userRoleList.stream().map(userRole -> userRole.getRole().getName()).toList();

        return new UserResponse(jwtService.generateToken(user.getUsername(), userRoleList),
                userMapper.toDto(user),
                roles
        );

    }
}
