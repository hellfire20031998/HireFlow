package com.hellFire.AuthService.dto.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phone;
}
