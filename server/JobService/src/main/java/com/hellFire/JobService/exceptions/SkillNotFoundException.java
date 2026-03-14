package com.hellFire.JobService.exceptions;

public class SkillNotFoundException extends BusinessException{
    public SkillNotFoundException( String message) {
        super(ErrorCode.SKILL_NOT_FOUND, message);
    }
}
