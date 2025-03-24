package com.example.school.service;

import com.example.school.entity.Workshop;

import java.util.List;

public interface WorkshopService {

    Workshop createWorkshop(Workshop workshop);

    Workshop updateWorkshop(Long id, Workshop updatedWorkshop);

    Workshop getWorkshopById(Long id);

    List<Workshop> getAllWorkshops();

    void deleteWorkshop(Long id);
}
