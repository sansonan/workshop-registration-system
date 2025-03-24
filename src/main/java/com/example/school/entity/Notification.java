package com.example.school.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;


@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String studentId;

    @Column(name = "type", nullable = false)
    private String type; // e.g., TELEGRAM, EMAIL, SMS

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "status", nullable = false)
    private String status; // e.g., PENDING, SENT, FAILED

    @Column(name = "failure_reason", columnDefinition = "TEXT")
    private String failureReason;

    @Column(name = "medium_details", columnDefinition = "TEXT")
    private String mediumDetails; // JSON string to store details like username, phone, etc.

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
