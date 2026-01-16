package com.hellFire.AuthService.exceptions;

public class UserNotFoundException extends BusinessException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
