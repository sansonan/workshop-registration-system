package com.example.school.service;

import com.example.school.entity.Notification;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;



public interface NotificationService {
    Notification createNotification(Notification notification);

    List<Notification> getNotificationsByUserId(Long userId);

    List<Notification> getNotificationsByStatus(String status);

    void updateNotificationStatus(Long id, String status, String failureReason);

}
