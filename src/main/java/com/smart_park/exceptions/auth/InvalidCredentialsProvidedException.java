package com.smart_park.exceptions.auth;

public class InvalidCredentialsProvidedException extends RuntimeException {
    public InvalidCredentialsProvidedException(String message) {
        super(message);
    }
}