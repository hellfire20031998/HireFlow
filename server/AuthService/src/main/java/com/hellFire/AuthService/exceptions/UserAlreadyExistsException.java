package com.hellFire.AuthService.exceptions;

public class UserAlreadyExistsException extends BusinessException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
