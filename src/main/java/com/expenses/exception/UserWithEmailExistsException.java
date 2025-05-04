package com.expenses.exception;

public class UserWithEmailExistsException extends RuntimeException{
    public UserWithEmailExistsException() {
    }

    public UserWithEmailExistsException(String message) {
        super(message);
    }

}
