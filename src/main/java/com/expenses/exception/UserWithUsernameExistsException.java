package com.expenses.exception;

public class UserWithUsernameExistsException extends RuntimeException{
    public UserWithUsernameExistsException() {
    }

    public UserWithUsernameExistsException(String message) {
        super(message);
    }
}
