package com.example.school.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException{
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, Long id) {
        super(HttpStatus.NOT_FOUND, String.format("%s With id = %d not found",resourceName,id ));
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(HttpStatus.NOT_FOUND, String.format("%s not found with %s : '%s'"),resourceName,fieldName,fieldValue);

    }
    public ResourceNotFoundException(String resourceName){
        super(HttpStatus.NOT_FOUND,String.format(resourceName));
    }

}
