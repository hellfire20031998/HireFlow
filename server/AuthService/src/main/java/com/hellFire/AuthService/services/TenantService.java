package com.hellFire.AuthService.services;

import com.hellFire.AuthService.dto.TenantDto;
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

    public TenantDto createTenant(CreateTenantRequest tenantRequest) {
        Tenant tenant = tenantMapper.toEntity(tenantRequest);
        tenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(tenant);
    }

    public TenantDto updateTenant(Long id, CreateTenantRequest tenantRequest) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + id));

        tenantMapper.updateTenantFromRequest(tenant, tenantRequest);

        tenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(tenant);
    }
}

