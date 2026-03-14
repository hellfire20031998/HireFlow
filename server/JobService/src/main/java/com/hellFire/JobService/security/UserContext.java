package com.hellFire.JobService.security;

import lombok.Data;
import java.util.List;

@Data
public class UserContext {
    private Long userId;
    private String username;
    private List<String> roles;
    private Long tenantId;
}