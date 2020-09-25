package com.example.spring5mvcrest.controllers.v1;

import com.example.spring5mvcrest.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFound(NotFoundException e) {
        return new ResponseEntity(e.getExceptionMessage(), HttpStatus.NOT_FOUND);
    }

}