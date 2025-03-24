package com.example.school.service.util;


import com.example.school.dto.UserDTO;
import com.example.school.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toUserDto (User user);
    User toUser(UserDTO userDTO);
}
