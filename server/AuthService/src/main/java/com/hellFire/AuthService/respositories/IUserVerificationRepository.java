package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserVerificationRepository extends JpaRepository<UserVerification, Long> {
    Optional<UserVerification> findByUser_IdAndDeleted(Long userId, boolean deleted);
}
