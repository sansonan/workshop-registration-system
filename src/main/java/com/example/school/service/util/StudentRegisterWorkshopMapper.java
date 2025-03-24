package com.example.school.service.util;

import com.example.school.dto.StudentRegisterWorkshopDTO;
import com.example.school.entity.StudentRegisterWorkshop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.school.entity.StudentRegisterWorkshop;
import com.example.school.entity.Workshop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
@Mapper(componentModel = "spring")
public interface StudentRegisterWorkshopMapper {

    @Mapping(source = "workshop.id", target = "workshopId")
    StudentRegisterWorkshopDTO toDto(StudentRegisterWorkshop entity);

    @Mapping(source = "workshopId", target = "workshop.id")
    StudentRegisterWorkshop toEntity(StudentRegisterWorkshopDTO dto);
}


