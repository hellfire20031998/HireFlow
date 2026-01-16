package com.hellFire.AuthService.services;

import com.hellFire.AuthService.exceptions.BusinessException;
import com.hellFire.AuthService.exceptions.ErrorCode;
import com.hellFire.AuthService.model.Role;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.model.UserRole;
import com.hellFire.AuthService.respositories.IRoleRepository;
import com.hellFire.AuthService.respositories.IUserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    private final IUserRoleRepository userRoleRepository;
    private final IRoleRepository roleRepository;

    public UserRoleService(IUserRoleRepository userRoleRepository, IRoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    public Role getRole(String roleName) {
        Optional<Role> optionalRole = roleRepository.findByNameContainingIgnoreCaseAndDeleted(roleName, false);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }
        throw new BusinessException(ErrorCode.ROLE_NOT_ASSIGNED, "Role not found");
    }

    public Role getRole(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }
        throw new BusinessException(ErrorCode.ROLE_NOT_ASSIGNED, "Role not found");
    }

    public List<Role> getAllRoles(List<Long> roleIds) {
        return roleRepository.findAllById(roleIds);
    }

    public void assignRoleToUser(User user, List<Role> roles) {

        List<UserRole> userRoles = roles.stream()
                .filter(role ->
                        !userRoleRepository.existsByUser_IdAndRole_IdAndActive(
                                user.getId(), role.getId(), true
                        )
                )
                .map(role -> {
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(role);
                    return userRole;
                })
                .toList();

        if (!userRoles.isEmpty()) {
            userRoleRepository.saveAll(userRoles);
        }
    }

    public List<UserRole> getUserRoles(Long userId) {
        List<UserRole> roles = userRoleRepository.findAllByUser_IdAndActive(userId, true);

        if (roles.isEmpty()) {
            return new ArrayList<>();
        }

        return roles;
    }
}
