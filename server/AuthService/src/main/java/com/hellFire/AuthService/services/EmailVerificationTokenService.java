package com.hellFire.AuthService.services;

import com.hellFire.AuthService.exceptions.BusinessException;
import com.hellFire.AuthService.exceptions.ErrorCode;
import com.hellFire.AuthService.model.EmailVerificationToken;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.respositories.IEmailVerificationTokenRepository;
import com.hellFire.AuthService.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class EmailVerificationTokenService {

    private final IEmailVerificationTokenRepository emailVerificationTokenRepository;

    public EmailVerificationTokenService(IEmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @Transactional
    public EmailVerificationToken getToken(User user) {

        EmailVerificationToken token =
                emailVerificationTokenRepository.findByUser_IdAndUsedAndDeleted(
                        user.getId(), false, false
                );

        if (token == null) {
            return createToken(user);
        }
        if (token.getExpiresAt().isBefore(Instant.now())) {
            emailVerificationTokenRepository.delete(token);
            return createToken(user);
        }
        return token;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EmailVerificationToken createToken(User user) {

        EmailVerificationToken token =
                emailVerificationTokenRepository.findByUser_IdAndUsedAndDeleted(
                        user.getId(), false, false
                );

        if (token == null) {
            return generateNewToken(user);
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            emailVerificationTokenRepository.delete(token);
            return generateNewToken(user);
        }

        return token;
    }


    public void verifyToken(User user, String token) {

        EmailVerificationToken emailVerificationToken =
                emailVerificationTokenRepository
                        .findByUser_IdAndUsedAndDeleted(user.getId(), false, false);

        if (emailVerificationToken == null) {
            throw new BusinessException(
                    ErrorCode.TOKEN_NOT_FOUND,
                    "No active email verification token found"
            );
        }

//        if (!isValidTokenFormat(token)) {
//            throw new BusinessException(
//                    ErrorCode.TOKEN_INVALID,
//                    "Invalid token format"
//            );
//        }

        if (!emailVerificationToken.getToken().equals(token)) {
            throw new BusinessException(
                    ErrorCode.TOKEN_INVALID,
                    "Token does not match"
            );
        }

        if (emailVerificationToken.getExpiresAt().isBefore(Instant.now())) {
            throw new BusinessException(
                    ErrorCode.TOKEN_EXPIRED,
                    "Token has expired"
            );
        }

        emailVerificationToken.setUsed(true);
        emailVerificationTokenRepository.save(emailVerificationToken);
    }

    private EmailVerificationToken generateNewToken(User user) {

        emailVerificationTokenRepository.deleteAllByUser_Id(user.getId());

        EmailVerificationToken token = new EmailVerificationToken();
        token.setUser(user);
        token.setToken(Utils.generateOtp());
        token.setExpiresAt(Instant.now().plusSeconds(600));

        return emailVerificationTokenRepository.save(token);
    }


}
