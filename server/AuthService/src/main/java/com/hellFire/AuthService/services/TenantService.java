package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.requests.CreateTenantRequest;
import com.hellFire.AuthService.mapper.ITenantMapper;
import com.hellFire.AuthService.model.Tenant;
import com.hellFire.AuthService.respositories.ITenantRepository;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final ITenantRepository tenantRepository;
    private final ITenantMapper tenantMapper;

    public TenantService(ITenantRepository tenantRepository, ITenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    public Tenant createTenant(CreateTenantRequest tenantRequest) {
        Tenant tenant = tenantMapper.toEntity(tenantRequest);
        tenant = tenantRepository.save(tenant);
        return tenant;
    }
}
