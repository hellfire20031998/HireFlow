package com.hellFire.AuthService.services;

import com.hellFire.AuthService.model.LoginAudit;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.respositories.ILoginAuditRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginAuditService {

    private final ILoginAuditRepository loginAuditRepository;

    public LoginAuditService(ILoginAuditRepository loginAuditRepository) {
        this.loginAuditRepository = loginAuditRepository;
    }

    public void log(User user, String ip, String userAgent, boolean success, String message) {

        LoginAudit audit = new LoginAudit();
        audit.setUser(user);
        audit.setIpAddress(ip);
        audit.setUserAgent(userAgent);
        audit.setSuccessful(success);
        audit.setMessage(message);

        loginAuditRepository.save(audit);
    }
}
