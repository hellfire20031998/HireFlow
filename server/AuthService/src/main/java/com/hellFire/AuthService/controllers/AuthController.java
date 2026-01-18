package com.hellFire.AuthService.controllers;

import com.hellFire.AuthService.dto.requests.CreateUserRequest;
import com.hellFire.AuthService.dto.requests.LoginRequest;
import com.hellFire.AuthService.dto.responses.ApiResponse;
import com.hellFire.AuthService.dto.responses.IdentityResponse;
import com.hellFire.AuthService.dto.responses.UserResponse;
import com.hellFire.AuthService.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody LoginRequest loginRequest,
                                                           HttpServletRequest request) {
        UserResponse response = authService.login(loginRequest.getUsername(), loginRequest.getPassword(), request);
        return ResponseEntity.ok(
                ApiResponse.success(response, "Login successful")
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody CreateUserRequest createUserRequest) {
        UserResponse response = authService.register(createUserRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "User registered successfully"));
    }

    @GetMapping("/validation")
    public ResponseEntity<ApiResponse<IdentityResponse>> validation(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : authHeader;
        return ResponseEntity.ok(ApiResponse.success(authService.userVerification(token), "User validation successful"));
    }

}