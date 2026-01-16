package com.hellFire.AuthService.dto.responses;

import com.hellFire.AuthService.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private String accessToken;
    private UserDto user;
    private List<String> roles;
}
