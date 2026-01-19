package com.hellFire.AuthService.exceptions;


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
    // USER / ACCOUNT
    // =========================
    USER_ALREADY_EXISTS,
    USER_NOT_FOUND,
    USER_DISABLED,
    EMAIL_NOT_VERIFIED,
    PHONE_NOT_VERIFIED,

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

    // =========================
    // SYSTEM / DATABASE
    // =========================
    PERSISTENCE_ERROR,
    INTERNAL_ERROR
}