package com.hellFire.AuthService.exceptions;

public class InvalidCredentialsException extends BusinessException{
    public InvalidCredentialsException(String message) {
        super(ErrorCode.INVALID_CREDENTIALS ,message);
    }
}
