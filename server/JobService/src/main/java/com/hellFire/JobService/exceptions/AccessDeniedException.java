package com.hellFire.JobService.exceptions;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }
}
