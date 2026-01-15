package com.hellFire.AuthService.dto;


import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.Instant;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseEntityDto {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean active;
    private boolean deleted;
}
