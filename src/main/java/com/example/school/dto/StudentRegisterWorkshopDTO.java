package com.example.school.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StudentRegisterWorkshopDTO {
    private Long id;
    private LocalDateTime scheduleTime;
    private String telegramId;
    private String telegramUsername;
    private String phoneEmail;
    private String studentId;
    private String gender;
    private Long workshopId; // Keep this as Long
    private LocalDateTime registerAt;

}
