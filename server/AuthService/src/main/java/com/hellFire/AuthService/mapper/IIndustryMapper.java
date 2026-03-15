package com.hellFire.AuthService.mapper;

import com.hellFire.AuthService.dto.IndustryDto;
import com.hellFire.AuthService.model.Industry;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IIndustryMapper {
    IndustryDto toDto(Industry industry);
}
