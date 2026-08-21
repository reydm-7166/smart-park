package com.smart_park.exceptions.record;

public class ParkingRecordAlreadyCheckoutException extends RuntimeException {
    public ParkingRecordAlreadyCheckoutException(String message) {
        super(message);
    }
}
