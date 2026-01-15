package com.hellFire.AuthService.mapper;

import com.hellFire.AuthService.dto.UserDto;
import com.hellFire.AuthService.dto.requests.CreateUserRequest;
import com.hellFire.AuthService.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserDto toDto(User user);
    User toEntity(CreateUserRequest request);
}
