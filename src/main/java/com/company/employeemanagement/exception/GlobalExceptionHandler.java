package com.company.employeemanagement.exception;

import com.company.employeemanagement.controller.LeaveController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex,
                                                  HttpServletRequest request) {

        log.error("Unhandled Exception");
        log.error("URL: {}", request.getRequestURI());
        log.error("Method: {}", request.getMethod());
        log.error("Exception Type: {}", ex.getClass().getName());
        log.error("Message: {}", ex.getMessage(), ex); // prints stack trace

        return ResponseEntity
                .badRequest()
                .body("Something went wrong. Please contact support.");
    }
}

