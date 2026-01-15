package com.hellFire.AuthService.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(
        name = "roles"
)
public class Role extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;
}
