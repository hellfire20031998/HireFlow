package com.hellFire.AuthService.services;


import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.model.UserVerification;
import com.hellFire.AuthService.respositories.IUserVerificationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserVerificationService {

    private final IUserVerificationRepository userVerificationRepository;

    public UserVerificationService(IUserVerificationRepository userVerificationRepository) {
        this.userVerificationRepository = userVerificationRepository;
    }

    public void markEmailVerified(User user) {

        UserVerification verification = userVerificationRepository.findByUser_IdAndDeleted(user.getId(), false)
                .orElseGet(() -> createInitialVerification(user));

        if (!verification.isEmailVerified()) {
            verification.setEmailVerified(true);
            verification.setEmailVerifiedAt(Instant.now());
        }

        userVerificationRepository.save(verification);
    }

    private UserVerification createInitialVerification(User user) {
        UserVerification verification = new UserVerification();
        verification.setUser(user);
        return verification;
    }
}
