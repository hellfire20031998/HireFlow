package com.hellFire.AuthService.services;

import com.hellFire.AuthService.exceptions.BusinessException;
import com.hellFire.AuthService.exceptions.ErrorCode;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.utils.Utils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class EmailVerificationTokenService {

    private static final long OTP_TTL_SECONDS = 300;

    // In-memory OTP store that mimics Redis-style key TTL behavior.
    private final ConcurrentMap<Long, OtpEntry> otpStore = new ConcurrentHashMap<>();

    public String getToken(User user) {
        long userId = user.getId();
        Instant now = Instant.now();

        OtpEntry current = otpStore.get(userId);
        if (current != null && current.expiresAt().isAfter(now)) {
            return current.token();
        }

        OtpEntry next = generateNewToken();
        otpStore.put(userId, next);
        return next.token();
    }


    public void verifyToken(User user, String token) {
        long userId = user.getId();

        OtpEntry otpEntry = otpStore.get(userId);

        if (otpEntry == null) {
            throw new BusinessException(
                    ErrorCode.TOKEN_NOT_FOUND,
                    "No active email verification token found"
            );
        }

        if (!otpEntry.token().equals(token)) {
            throw new BusinessException(
                    ErrorCode.TOKEN_INVALID,
                    "Token does not match"
            );
        }

        if (otpEntry.expiresAt().isBefore(Instant.now())) {
            otpStore.remove(userId, otpEntry);
            throw new BusinessException(
                    ErrorCode.TOKEN_EXPIRED,
                    "Token has expired"
            );
        }

        // One-time use token: remove it after successful verification.
        otpStore.remove(userId, otpEntry);
    }

    private OtpEntry generateNewToken() {
        return new OtpEntry(
                Utils.generateOtp(),
                Instant.now().plusSeconds(OTP_TTL_SECONDS)
        );
    }

    private record OtpEntry(String token, Instant expiresAt) {}

}
