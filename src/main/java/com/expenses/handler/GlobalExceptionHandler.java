package com.expenses.handler;

import com.expenses.exception.UserNotFoundException;
import com.expenses.exception.UserWithEmailExistsException;
import com.expenses.exception.UserWithUsernameExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(UserWithEmailExistsException.class)
    public ResponseEntity<String> handleUserWithEmailExistsException(UserWithEmailExistsException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }
    @ExceptionHandler(UserWithUsernameExistsException.class)
    public ResponseEntity<String> handleUserWithUsernameExistsException(UserWithUsernameExistsException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }
}
