package com.hellFire.JobService.exceptions;


public enum ErrorCode {

    // =========================
    // AUTHENTICATION / SECURITY
    // =========================
    AUTH_ERROR,
    INVALID_CREDENTIALS,
    UNAUTHORIZED,
    TOKEN_EXPIRED,
    TOKEN_INVALID,

    // =========================
    // VALIDATION / INPUT
    // =========================
    BAD_REQUEST,
    VALIDATION_ERROR,
    CONSTRAINT_VIOLATION,
    DATA_INTEGRITY_ERROR,

    // =========================
    // BUSINESS LOGIC
    // =========================
    BUSINESS_ERROR,
    OPERATION_NOT_ALLOWED,
    ROLE_NOT_ASSIGNED,
    ROLE_NOT_FOUND,
    FORBIDDEN,
    TOKEN_NOT_FOUND,
    TOKEN_ALREADY_USED,
    JOB_NOT_FOUND,
    SKILL_NOT_FOUND,

    // =========================
    // SYSTEM / DATABASE
    // =========================
    PERSISTENCE_ERROR,
    INTERNAL_ERROR
}