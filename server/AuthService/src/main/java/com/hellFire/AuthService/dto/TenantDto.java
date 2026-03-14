package com.hellFire.AuthService.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hellFire.AuthService.model.enums.BillingStatus;
import com.hellFire.AuthService.model.enums.CompanySize;
import com.hellFire.AuthService.model.enums.Plan;
import com.hellFire.AuthService.model.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TenantDto extends BaseEntityDto {
    private String tenantName;
    private String tenantSlug;
    private String domain;
    private String industry;
    private CompanySize companySize;
    private String country;
    private String countryCode;
    private String primaryEmail;
    private String contactPhone;
    private String websiteUrl;
    private Plan plan;
    private BillingStatus billingStatus;
    private Instant trialEndsAt;
    private Status status;
}

