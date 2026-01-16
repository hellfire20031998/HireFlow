package com.hellFire.AuthService.respositories;

import com.hellFire.AuthService.model.LoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoginAuditRepository extends JpaRepository<LoginAudit, Long> {
}
