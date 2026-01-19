package com.hellFire.AuthService.services;

import com.hellFire.AuthService.model.EmailVerificationToken;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.respositories.IEmailVerificationTokenRepository;
import com.hellFire.AuthService.utils.Utils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmailVerificationTokenService {

    private final IEmailVerificationTokenRepository emailVerificationTokenRepository;

    public EmailVerificationTokenService(IEmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    public EmailVerificationToken getToken(User user) {
        EmailVerificationToken token =
                emailVerificationTokenRepository.findByUser_IdAndUsedAndDeleted(
                        user.getId(), false, false
                );

        if (token == null) {
            return createToken(user);
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            return createToken(user);
        }

        return token;
    }


    public EmailVerificationToken createToken(User user) {
        EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByUser_IdAndUsedAndDeleted(user.getId(), false, false);
        if (emailVerificationToken == null || emailVerificationToken.getExpiresAt().isBefore(Instant.now())) {
            emailVerificationToken = new EmailVerificationToken();
            emailVerificationToken.setUser(user);
            emailVerificationToken.setToken(Utils.generateOtp());
            emailVerificationToken.setExpiresAt(Instant.now().plusSeconds(60));
            emailVerificationToken = emailVerificationTokenRepository.save(emailVerificationToken);
        }
        return emailVerificationToken;
    }

    public boolean verifyToken(User user, String token) {
        EmailVerificationToken emailVerificationToken =
                emailVerificationTokenRepository.findByUser_IdAndUsedAndDeleted(user.getId(), false, false);

        if (emailVerificationToken == null) {
            return false;
        }

        if (!emailVerificationToken.getToken().equals(token)) {
            return false;
        }

        if (emailVerificationToken.getExpiresAt().isBefore(Instant.now())) {
            return false;
        }
        emailVerificationToken.setUsed(true);
        emailVerificationTokenRepository.save(emailVerificationToken);

        return true;
    }

}
