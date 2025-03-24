package com.example.school.service.impl;


import com.example.school.service.TelegramNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramNotificationServiceImpl implements TelegramNotificationService {
    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.chat.id}")
    private String chatId;
    private final String TELEGRAM_API_BASE = "https://api.telegram.org/bot";

   // private final String telegramApiUrl = "https://api.telegram.org/bot" + botToken;
    private final String botTokens = "";
    private final String telegramApiUrl = "https://api.telegram.org/bot" + botTokens;


    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public void sendNotification(String message) {
        String apiUrl = "https://api.telegram.org/bot" + botToken + "/sendMessage";
        String payload = String.format("?chat_id=%s&text=%s", chatId, message);

        try {
            restTemplate.getForObject(apiUrl + payload, String.class);
            System.out.println("Notification sent successfully.");
        } catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }
    }

    @Override
    public void sendNotification (String telegramName, String message) {
        try {
            // Build the Telegram API URL
            String url = TELEGRAM_API_BASE + botToken + "/sendMessage?chat_id=" + telegramName + "&text=" + message;

            // Make the request
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(url, String.class);

            System.out.println("Message sent to Telegram ID: " + telegramName);
        } catch (Exception e) {
            System.err.println("Failed to send message to Telegram ID: " + telegramName + ". Error: " + e.getMessage());
        }
    }

    @Override
    public void sendMessageByUsername(String telegramUsername, String telegramMessage) {
        // Ensure username doesn't contain '@'
        if (telegramUsername.startsWith("@")) {
            telegramUsername = telegramUsername.substring(1);
        }

        // Build the URL to send the message
        String url = String.format("%s/sendMessage?chat_id=@%s&text=%s",
                telegramApiUrl, telegramUsername, telegramMessage);


        try {
            // Use RestTemplate to send the HTTP GET request
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            // Log response for debugging (can be removed in production)
            System.out.println("Telegram API Response: " + response);
        } catch (Exception e) {
            System.err.println("Failed to send Telegram message to @" + telegramUsername + ": " + e.getMessage());
        }
    }


}
