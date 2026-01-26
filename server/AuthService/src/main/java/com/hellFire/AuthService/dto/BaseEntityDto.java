package com.hellFire.AuthService.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

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
