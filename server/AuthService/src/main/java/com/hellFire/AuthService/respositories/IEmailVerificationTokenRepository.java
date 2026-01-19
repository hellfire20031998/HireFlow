package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    EmailVerificationToken findByUser_IdAndUsedAndDeleted(Long userId, boolean used, boolean deleted);
}
