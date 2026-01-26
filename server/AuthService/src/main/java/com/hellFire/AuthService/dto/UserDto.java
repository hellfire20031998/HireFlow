package com.hellFire.AuthService.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
