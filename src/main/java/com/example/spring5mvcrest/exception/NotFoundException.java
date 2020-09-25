package com.example.spring5mvcrest.exception;

public class NotFoundException extends RuntimeException {

    private Long id;

    public NotFoundException(Long id) {
        this.id = id;
    }

    public String getExceptionMessage() {
        return "Resource is not found. Id: " + id;
    }

}