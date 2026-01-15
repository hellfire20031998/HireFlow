package com.hellFire.AuthService.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "user_verifications",
        uniqueConstraints = @UniqueConstraint(columnNames = "user_id")
)
public class UserVerification extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private boolean emailVerified = false;

    private Instant emailVerifiedAt;

    @Column(nullable = false)
    private boolean phoneVerified = false;

    private Instant phoneVerifiedAt;

    // ---- KYC / IDENTITY (optional, future-ready) ----
    @Column(nullable = false)
    private boolean identityVerified = false;

    private Instant identityVerifiedAt;

    // ---- TWO-FACTOR / MFA ----
    @Column(nullable = false)
    private boolean mfaEnabled = false;

    private Instant mfaEnabledAt;
}