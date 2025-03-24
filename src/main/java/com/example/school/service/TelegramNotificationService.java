package com.example.school.service;

public interface TelegramNotificationService {
    void sendNotification(String message);
    void sendNotification (String telegramName, String message);
    void sendMessageByUsername(String telegramUsername, String telegramMessage);
}
