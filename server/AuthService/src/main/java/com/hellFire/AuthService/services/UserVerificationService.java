package com.hellFire.AuthService.services;


import com.hellFire.AuthService.respositories.IUserVerificationRepository;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationService {

    private final IUserVerificationRepository userVerificationRepository;

    public UserVerificationService(IUserVerificationRepository userVerificationRepository) {
        this.userVerificationRepository = userVerificationRepository;
    }
}
