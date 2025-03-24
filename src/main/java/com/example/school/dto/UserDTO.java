package com.example.school.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {

        @NotBlank
        @Size(max = 50)
        private String firstName;

        @NotBlank
        @Size(max = 50)
        private String lastName;

        @NotBlank
        @Size(max = 50)
        private String studentId;

        @NotNull
        private LocalDate dateOfBirth;

        @NotBlank
        @Size(max = 50)
        private String occupation;

        @NotBlank
        @Size(max = 15)
        private String phoneNumber;

        @NotBlank
        @Email
        @Size(max = 50)
        private String email;

        @NotBlank
        @Size(min = 8, max = 100)
        private String password;

        @NotBlank
        @Size(min = 8, max = 100)
        private String confirmPassword;

        @NotBlank
        @Size(max = 100)
        private String address;

}
