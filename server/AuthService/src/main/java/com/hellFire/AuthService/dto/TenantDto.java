package com.hellFire.AuthService.dto;

import com.hellFire.AuthService.model.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TenantDto extends BaseEntityDto {
    private String tenantName;
    private Status status;
}
