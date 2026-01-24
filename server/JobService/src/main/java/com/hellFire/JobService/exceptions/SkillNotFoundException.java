package com.hellFire.JobService.exceptions;

public class SkillNotFoundException extends BusinessException{
    public SkillNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
