package com.hellFire.AuthService.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "login_audit")
public class LoginAudit extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 50)
    private String ipAddress;

    @Column(length = 300)
    private String userAgent;

    @Column(nullable = false)
    private boolean successful;

    @Column(length = 200)
    private String message;
}