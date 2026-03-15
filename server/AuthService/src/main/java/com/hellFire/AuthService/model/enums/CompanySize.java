package com.hellFire.AuthService.model.enums;

import lombok.Getter;

@Getter
public enum CompanySize {

    MICRO(1, 10),
    SMALL(11, 50),
    MEDIUM(51, 100),
    LARGE(101, 500),
    ENTERPRISE(501, Integer.MAX_VALUE);

    private final int minEmployees;
    private final int maxEmployees;

    CompanySize(int minEmployees, int maxEmployees) {
        this.minEmployees = minEmployees;
        this.maxEmployees = maxEmployees;
    }

}