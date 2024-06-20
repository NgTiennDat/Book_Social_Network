package com.datien.booknetwork.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//@ExceptionHandler
public class GlobalExceptionHandler {

    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {

    }

}
