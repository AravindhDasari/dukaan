package com.dukaan.userservice.config;

import com.dukaan.userservice.domain.model.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Message> handleIllegalArgumentException (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Message(e.getMessage()));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<Message> handleIllegalAccessException (IllegalAccessException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Message(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleException (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Message(e.getMessage()));
    }

}
