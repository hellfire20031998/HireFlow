package com.hellFire.AuthService.mapper;

import com.hellFire.AuthService.dto.TenantDto;
import com.hellFire.AuthService.dto.requests.CreateTenantRequest;
import com.hellFire.AuthService.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = IIndustryMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ITenantMapper {
    TenantDto toDto(Tenant tenant);

    @Mapping(target = "industries", ignore = true)
    Tenant toTenant(TenantDto dto);

    @Mapping(target = "industries", ignore = true)
    Tenant toEntity(CreateTenantRequest request);

    @Mapping(target = "industries", ignore = true)
    void updateTenantFromRequest(@MappingTarget Tenant tenant, CreateTenantRequest request);
}
