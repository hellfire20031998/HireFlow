package com.hellFire.AuthService.model;

import com.hellFire.AuthService.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tenants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant extends BaseEntity {

    @Column(name = "tenant_name", nullable = false, length = 150)
    private String tenantName;

    @Enumerated(EnumType.STRING)
    private Status status;
}
