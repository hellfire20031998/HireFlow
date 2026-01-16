package com.hellFire.AuthService.exceptions;

public class UserAlreadyExistsException extends BusinessException{
    public UserAlreadyExistsException(String message) {
        super(ErrorCode.USER_ALREADY_EXISTS, message);
    }
}
