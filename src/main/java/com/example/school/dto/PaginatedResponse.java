package com.example.school.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    // Constructors, Getters, Setters
}
