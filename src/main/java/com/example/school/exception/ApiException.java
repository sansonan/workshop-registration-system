package com.example.school.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException{
    private final HttpStatus status;
    private final String message;
    public ApiException(HttpStatus status, String message, Object... args) {
        this.status = status;
        this.message = String.format(message, args);
    }
}
