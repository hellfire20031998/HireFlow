package com.hellFire.JobService.models.enums;

/**
 * Must match AuthService UserType enum values (set via X-User-Type header from JWT).
 */
public enum UserType {
    SYSTEM,
    CANDIDATE,
    INTERVIEWER,
    CLIENT
}
