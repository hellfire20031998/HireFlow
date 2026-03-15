package com.hellFire.AuthService.dto.requests;

import com.hellFire.AuthService.model.enums.CompanySize;
import com.hellFire.AuthService.model.enums.Plan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateTenantRequest {

    @NotBlank(message = "Tenant name is required")
    @Size(max = 150, message = "Tenant name must not exceed 150 characters")
    private String tenantName;

    @NotBlank(message = "Tenant slug is required")
    @Size(max = 100, message = "Tenant slug must not exceed 100 characters")
    private String tenantSlug;
    private String domain;
    /** Industry IDs from the industries table; a tenant can have multiple. */
    private List<Long> industryIds;
    private CompanySize companySize;
    private String country;
    private String countryCode;
    private String primaryEmail;
    private String contactPhone;
    private String websiteUrl;
    private Plan plan;
}

