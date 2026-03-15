package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.TenantDto;
import com.hellFire.AuthService.dto.requests.CreateTenantRequest;
import com.hellFire.AuthService.mapper.ITenantMapper;
import com.hellFire.AuthService.model.Industry;
import com.hellFire.AuthService.model.Tenant;
import com.hellFire.AuthService.model.User;
import com.hellFire.AuthService.model.enums.BillingStatus;
import com.hellFire.AuthService.model.enums.Plan;
import com.hellFire.AuthService.model.enums.Status;
import com.hellFire.AuthService.respositories.IIndustryRepository;
import com.hellFire.AuthService.respositories.ITenantRepository;
import com.hellFire.AuthService.respositories.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class TenantService {

    private final ITenantRepository tenantRepository;
    private final IIndustryRepository industryRepository;
    private final IUserRepository userRepository;
    private final ITenantMapper tenantMapper;

    public TenantService(ITenantRepository tenantRepository,
                         IIndustryRepository industryRepository,
                         IUserRepository userRepository,
                         ITenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.industryRepository = industryRepository;
        this.userRepository = userRepository;
        this.tenantMapper = tenantMapper;
    }

    @Transactional
    public TenantDto createTenant(CreateTenantRequest tenantRequest, User currentUser) {
        Tenant tenant = tenantMapper.toEntity(tenantRequest);
        tenant.setIndustries(resolveIndustries(tenantRequest.getIndustryIds()));
        tenant.setPlan(Plan.FREE);
        tenant.setBillingStatus(BillingStatus.ACTIVE);
        tenant.setStatus(Status.ACTIVE);
        tenant = tenantRepository.save(tenant);

        if (currentUser != null && currentUser.getId() != null) {
            User user = userRepository.findById(currentUser.getId())
                    .orElse(null);
            if (user != null) {
                user.setTenant(tenant);
                userRepository.save(user);
            }
        }

        return tenantMapper.toDto(tenant);
    }

    public TenantDto updateTenant(Long id, CreateTenantRequest tenantRequest) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + id));

        tenantMapper.updateTenantFromRequest(tenant, tenantRequest);
        if (tenantRequest.getIndustryIds() != null) {
            tenant.setIndustries(resolveIndustries(tenantRequest.getIndustryIds()));
        }

        tenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(tenant);
    }

    private List<Industry> resolveIndustries(List<Long> industryIds) {
        if (industryIds == null || industryIds.isEmpty()) {
            return Collections.emptyList();
        }
        return industryRepository.findAllById(industryIds);
    }
}

