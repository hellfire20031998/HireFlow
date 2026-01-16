package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByUser_IdAndActive(Long userId, boolean active);

    boolean existsByUser_IdAndRole_IdAndActive(Long userId, Long roleId, boolean active);
}
