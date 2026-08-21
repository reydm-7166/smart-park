package com.smart_park.exceptions.record;

public class ParkingRecordNotExistingException extends RuntimeException {
    public ParkingRecordNotExistingException(String message) {
        super(message);
    }
}