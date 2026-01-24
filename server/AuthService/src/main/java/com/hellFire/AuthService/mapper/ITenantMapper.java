package com.hellFire.AuthService.mapper;

import com.hellFire.AuthService.dto.TenantDto;
import com.hellFire.AuthService.dto.requests.CreateTenantRequest;
import com.hellFire.AuthService.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ITenantMapper {
    TenantDto toDto(Tenant tenant);

    Tenant toTenant(TenantDto dto);
    Tenant toEntity(CreateTenantRequest request);
}
