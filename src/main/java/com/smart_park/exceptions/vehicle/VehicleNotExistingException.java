package com.smart_park.exceptions.vehicle;

public class VehicleNotExistingException extends RuntimeException {
    public VehicleNotExistingException(String message) {
        super(message);
    }
}