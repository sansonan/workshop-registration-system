package com.example.school.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkshopDTO {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String information;
    private int seat;
    private String summary;


}

