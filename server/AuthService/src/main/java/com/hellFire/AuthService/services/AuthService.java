package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.UserDto;
import com.hellFire.AuthService.dto.requests.CreateUserRequest;
import com.hellFire.AuthService.dto.responses.IdentityResponse;
import com.hellFire.AuthService.dto.responses.UserResponse;
import com.hellFire.AuthService.exceptions.InvalidCredentialsException;
import com.hellFire.AuthService.exceptions.UserAlreadyExistsException;
import com.hellFire.AuthService.exceptions.UserNotFoundException;
import com.hellFire.AuthService.mapper.ITenantMapper;
import com.hellFire.AuthService.mapper.IUserMapper;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.model.UserRole;
import com.hellFire.AuthService.respositories.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final IUserMapper userMapper;
    private final ITenantMapper tenantMapper;
    private final UserRoleService userRoleService;
    private final LoginAuditService loginAuditService;
    private final TenantService tenantService;

    public AuthService(
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            IUserMapper userMapper, ITenantMapper tenantMapper,
            UserRoleService userRoleService,
            LoginAuditService loginAuditService, TenantService tenantService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.tenantMapper = tenantMapper;
        this.userRoleService = userRoleService;
        this.loginAuditService = loginAuditService;

        this.tenantService = tenantService;
    }

    @Transactional
    public UserResponse login(String username, String password, HttpServletRequest request) {

        User user = userRepository
                .findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        List<UserRole> userRoleList = userRoleService.getUserRoles(user.getId());
        List<String> roles = userRoleList.stream()
                .map(userRole -> userRole.getRole().getName())
                .toList();

        loginAuditService.log(
                user,
                request.getRemoteAddr(),
                request.getHeader("User-Agent"),
                true,
                "Login successful"
        );

        return new UserResponse(
                jwtService.generateToken(user, userRoleList),
                userMapper.toDto(user),
                roles
        );
    }

    @Transactional
    public UserResponse registerSystemUser(CreateUserRequest request) {
        User user = createBaseUser(request);
        user.setTenant(null);
        userRepository.save(user);
        return buildUserResponse(user);
    }

    @Transactional
    public UserResponse registerClientUser(CreateUserRequest request) {
        User user = createBaseUser(request);

        userRepository.save(user);
        return buildUserResponse(user);
    }

    @Transactional
    public UserResponse registerInterviewer(CreateUserRequest request) {
        User user = createBaseUser(request);
        userRepository.save(user);
        return buildUserResponse(user);
    }

    @Transactional
    public UserResponse registerCandidate(CreateUserRequest request) {
        User user = createBaseUser(request);

        user.setTenant(null);
        userRepository.save(user);
        return buildUserResponse(user);
    }

    private User createBaseUser(CreateUserRequest request) {

        boolean exists = userRepository
                .existsByUsernameOrEmail(request.getUsername(), request.getEmail());

        if (exists) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    private UserResponse buildUserResponse(User user) {
        List<UserRole> roles = userRoleService.getUserRoles(user.getId());
        List<String> roleNames = roles.stream().map(r -> r.getRole().getName()).toList();

        return new UserResponse(
                jwtService.generateToken(user, roles),
                userMapper.toDto(user),
                roleNames
        );
    }
    public IdentityResponse userVerification(String token) {
        jwtService.isTokenValid(token);
        String username = jwtService.extractUsername(token);
        Optional<User> userOptional = userRepository.findByUsernameAndDeletedFalse(username);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        List<UserRole> userRoleList = userRoleService.getUserRoles(user.getId());
        List<String> userRoles = userRoleList.stream().map(userRole -> userRole.getRole().getName()).collect(Collectors.toList());
        return new IdentityResponse(user.getId(),
                user.getUsername(),
                userRoles);
    }
}
