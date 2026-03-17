package com.hellFire.JobService.security;

import com.hellFire.JobService.models.enums.UserType;
import lombok.Data;

import java.util.List;

@Data
public class UserContext {
    private Long userId;
    private String username;
    private List<String> roles;
    private Long tenantId;
    private String tenantName;
    /** User type from AuthService, set from X-User-Type header. */
    private UserType userType;
}