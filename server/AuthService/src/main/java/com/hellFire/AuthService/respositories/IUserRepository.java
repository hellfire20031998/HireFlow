package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUsernameAndDeletedFalse(String username);

   boolean existsByUsernameOrEmail(String username, String email);

}
