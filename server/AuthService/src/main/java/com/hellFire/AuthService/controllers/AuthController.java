package com.hellFire.AuthService.controllers;

import com.hellFire.AuthService.dto.UserDto;
import com.hellFire.AuthService.dto.requests.CreateUserRequest;
import com.hellFire.AuthService.services.AuthService;
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
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        return authService.login(username, password);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(authService.register(createUserRequest), HttpStatus.CREATED);
    }
}