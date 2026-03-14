package com.hellFire.AuthService.dto.requests;

import com.hellFire.AuthService.model.enums.CompanySize;
import com.hellFire.AuthService.model.enums.Plan;
import lombok.Data;

@Data
public class CreateTenantRequest {
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
}

