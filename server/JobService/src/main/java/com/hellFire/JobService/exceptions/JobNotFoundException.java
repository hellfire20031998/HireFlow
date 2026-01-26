package com.hellFire.JobService.exceptions;

public class JobNotFoundException extends BusinessException{
    public JobNotFoundException( String message) {
        super(ErrorCode.JOB_NOT_FOUND, message);
    }
}
