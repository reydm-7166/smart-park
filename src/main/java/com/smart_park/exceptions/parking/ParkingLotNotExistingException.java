package com.smart_park.exceptions.parking;

public class ParkingLotNotExistingException extends RuntimeException {
    public ParkingLotNotExistingException(String message) {
        super(message);
    }
}