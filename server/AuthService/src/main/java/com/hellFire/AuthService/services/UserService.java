package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.responses.IdentityResponse;
import com.hellFire.AuthService.dto.responses.UserResponse;
import com.hellFire.AuthService.exceptions.BusinessException;
import com.hellFire.AuthService.exceptions.ErrorCode;
import com.hellFire.AuthService.exceptions.UserNotFoundException;
import com.hellFire.AuthService.mapper.IUserMapper;
import com.hellFire.AuthService.model.Role;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.model.UserRole;
import com.hellFire.AuthService.model.enums.RoleScope;
import com.hellFire.AuthService.respositories.IUserRepository;
import com.hellFire.AuthService.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final UserRoleService userRoleService;
    private final IUserMapper userMapper;
    private final JwtService jwtService;

    public UserService(IUserRepository userRepository,
                       UserRoleService userRoleService,
                       IUserMapper userMapper,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Transactional
    public void assignRoleToUser(User actingUser, Long userId, List<Long> roleIds) {

        User targetUser = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        List<Role> roles = userRoleService.getAllRoles(roleIds);

        if(CollectionUtils.isEmpty(roles)){
            throw new BusinessException(
                    ErrorCode.ROLE_NOT_FOUND,
                    "No roles found for id"
            );
        }

        for (Role role : roles) {

            if (!isSystemAdmin(actingUser) && role.getScope() == RoleScope.SYSTEM) {
                throw new BusinessException(
                        ErrorCode.FORBIDDEN,
                        "You are not allowed to assign system-level roles."
                );
            }
        }
        userRoleService.assignRoleToUser(targetUser, roles);
    }

    private boolean isSystemAdmin(User user) {
        return userRoleService.getUserRoles(user.getId()).stream()
                .anyMatch(ur ->
                        ur.getRole().getName().equals("ROLE_SYSTEM_ADMIN") ||
                                ur.getRole().getName().equals("ROLE_SYSTEM_SUPER_ADMIN")
                );
    }


}
