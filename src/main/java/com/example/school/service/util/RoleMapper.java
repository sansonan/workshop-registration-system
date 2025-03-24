package com.example.school.service.util;

import com.example.school.dto.RoleDTO;
import com.example.school.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    RoleDTO toRoleDto (Role role);
    Role toRole(RoleDTO roleDTO);


}
