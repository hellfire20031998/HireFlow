package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
   User findByUsernameAndDeleted(String username, boolean deleted);
   User findByUsernameOrEmail(String username, String email);
}
