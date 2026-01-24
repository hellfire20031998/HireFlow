package com.hellFire.AuthService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto extends BaseEntityDto{
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phone;
    private TenantDto tenant;
}
