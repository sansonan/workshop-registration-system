package com.example.school.repository;


import com.example.school.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    // Add custom queries if necessary
}
