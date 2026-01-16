package com.hellFire.AuthService.controllers;

import com.hellFire.AuthService.dto.responses.ApiResponse;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.services.UserService;
import com.hellFire.AuthService.utils.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{userId}/roles")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','SYSTEM_SUPER_ADMIN','CLIENT_ADMIN')")
    public ResponseEntity<ApiResponse<?>> assignRole(
            @PathVariable Long userId,
            @RequestBody List<Long> roleIds
    ) {
        User actingUser = SecurityUtil.getCurrentUser();
        userService.assignRoleToUser(actingUser, userId, roleIds);

        return ResponseEntity.ok(ApiResponse.success(null, "Roles assigned successfully"));
    }

}
