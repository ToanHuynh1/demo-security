package com.demo_security.demo_security.mapper;

import com.demo_security.demo_security.model.User;
import com.demo_security.demo_security.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
