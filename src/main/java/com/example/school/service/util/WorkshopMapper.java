package com.example.school.service.util;

import com.example.school.dto.WorkshopDTO;
import com.example.school.entity.Workshop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkshopMapper {
    WorkshopMapper INSTANCE = Mappers.getMapper(WorkshopMapper.class);

    WorkshopDTO toWorkshopDto (Workshop workshop);
    Workshop toWorkshop(WorkshopDTO workshopDTO);
}
