package com.hellFire.AuthService.model;

import com.hellFire.AuthService.model.enums.BillingStatus;
import com.hellFire.AuthService.model.enums.CompanySize;
import com.hellFire.AuthService.model.enums.Plan;
import com.hellFire.AuthService.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "tenants",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "tenant_slug")
        }
)
public class Tenant extends BaseEntity {

    @Column(name = "tenant_name", nullable = false, length = 150)
    private String tenantName;

    @Column(name = "tenant_slug", nullable = false, unique = true, length = 100)
    private String tenantSlug;

    @Column(name = "domain", length = 150)
    private String domain;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tenant_industries",
            joinColumns = @JoinColumn(name = "tenant_id"),
            inverseJoinColumns = @JoinColumn(name = "industry_id")
    )
    private List<Industry> industries = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "company_size", length = 50)
    private CompanySize companySize;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Column(name = "primary_email", length = 150)
    private String primaryEmail;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    @Column(name = "website_url", length = 255)
    private String websiteUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan", length = 50)
    private Plan plan;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_status", length = 50)
    private BillingStatus billingStatus;

    @Column(name = "trial_ends_at")
    private Instant trialEndsAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
