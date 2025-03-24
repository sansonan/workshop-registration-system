package com.example.school.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_register_workshop")
@Data
public class StudentRegisterWorkshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime scheduleTime; // The time when the student is scheduled for the workshop

    private String telegramUsername; // telegramUsername of the student

    private String telegramId;

    private String phoneEmail; // Phone number or email of the student

    private String studentId; // ID of the student

    private String gender; // Gender of the student

    @ManyToOne
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    private LocalDateTime registerAt; // Date and time of registration
    private String status;

}

