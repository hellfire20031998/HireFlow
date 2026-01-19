package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserVerificationRepository extends JpaRepository<UserVerification, Long> {
}
