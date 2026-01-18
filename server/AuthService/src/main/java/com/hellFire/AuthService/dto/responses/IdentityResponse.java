package com.hellFire.AuthService.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityResponse {
    private Long userId;
    private String username;
    private List<String> roles;
}
