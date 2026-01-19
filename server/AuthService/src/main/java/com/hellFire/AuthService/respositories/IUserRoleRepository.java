package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByUser_IdAndDeleted(Long userId, boolean deleted);

    boolean existsByUser_IdAndRole_IdAndDeleted(Long userId, Long roleId, boolean deleted);
}
