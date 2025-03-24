package com.example.school.service.impl;


import com.example.school.entity.Notification;
import com.example.school.entity.StudentRegisterWorkshop;
import com.example.school.entity.Workshop;
import com.example.school.repository.WorkshopRepository;
import com.example.school.service.NotificationService;
import com.example.school.service.TelegramNotificationService;
import com.example.school.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;

    @Autowired
    private TelegramNotificationService telegramService;

    @Autowired
    private NotificationService notificationService;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public Workshop createWorkshop(Workshop workshop) {
        return workshopRepository.save(workshop);
    }

    @Override
    public Workshop updateWorkshop(Long id, Workshop updatedWorkshop) {
        Workshop existingWorkshop = workshopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workshop not found with id: " + id));

        existingWorkshop.setTitle(updatedWorkshop.getTitle());
        existingWorkshop.setDescription(updatedWorkshop.getDescription());
        existingWorkshop.setStartDate(updatedWorkshop.getStartDate());
        existingWorkshop.setEndDate(updatedWorkshop.getEndDate());
        existingWorkshop.setLocation(updatedWorkshop.getLocation());
        existingWorkshop.setInformation(updatedWorkshop.getInformation());
        existingWorkshop.setSeat(updatedWorkshop.getSeat());
        existingWorkshop.setSummary(updatedWorkshop.getSummary());
        Workshop saveWorkshop = workshopRepository.save(existingWorkshop);

        // Notify registered students via Telegram
        notifyRegisteredStudents(saveWorkshop);
        // Send a notification via Telegram
//        String message = String.format(
//                "Workshop '%s' has been updated!\nNew Date: %s to %s\nLocation: %s",
//                saveWorkshop.getTitle(),
//                saveWorkshop.getStartDate(),
//                saveWorkshop.getEndDate(),
//                saveWorkshop.getLocation()
//        );
//        telegramService.sendNotification(message);

        return saveWorkshop;
    }

    private void notifyRegisteredStudents(Workshop workshop) {
        String message = String.format(
                "Workshop '%s' has been updated!\nNew Date: %s to %s\nLocation: %s",
                workshop.getTitle(),
                workshop.getStartDate(),
                workshop.getEndDate(),
                workshop.getLocation()
        );

        // Example: Send Telegram message to each registered student
        for (StudentRegisterWorkshop registration : workshop.getRegistrations()) {

            try {
//                telegramService.sendNotification(registration.getTelegramId(), message);
                telegramService.sendMessageByUsername(registration.getTelegramUsername(),message);

                // Save success notification
                Notification notification = new Notification();
                notification.setUserId(1l);
                notification.setStudentId(registration.getStudentId());
                notification.setType("TELEGRAM");
                notification.setMessage(message);
                notification.setSentAt(LocalDateTime.now());
                notification.setStatus("SENT");
                notification.setMediumDetails("{\"username\": \"" + registration.getTelegramUsername()+ "\"}");
                notificationService.createNotification(notification);

            } catch (Exception e) {
                // Save failure notification
                Notification notification = new Notification();
                notification.setStudentId(registration.getStudentId());
                notification.setType("TELEGRAM");
                notification.setMessage(message);
                notification.setSentAt(LocalDateTime.now());
                notification.setStatus("FAILED");
                notification.setFailureReason(e.getMessage());
                notification.setMediumDetails("{\"username\": \"" + registration.getTelegramUsername() + "\"}");
                notificationService.createNotification(notification);
            }
        }


//        for (StudentRegisterWorkshop registration : workshop.getRegistrations()) {
//            telegramService.sendMessageByUsername(registration.getTelegramUsername(), message);
//        }



    }

    @Override
    public Workshop getWorkshopById(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workshop not found with id: " + id));
    }

    @Override
    public List<Workshop> getAllWorkshops() {
        return workshopRepository.findAll();
    }

    @Override
    public void deleteWorkshop(Long id) {
        if (!workshopRepository.existsById(id)) {
            throw new IllegalArgumentException("Workshop not found with id: " + id);
        }
        workshopRepository.deleteById(id);
    }
}
