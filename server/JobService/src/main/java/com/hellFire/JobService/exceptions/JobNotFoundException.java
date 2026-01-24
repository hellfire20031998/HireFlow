package com.hellFire.JobService.exceptions;

public class JobNotFoundException extends BusinessException{
    public JobNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
