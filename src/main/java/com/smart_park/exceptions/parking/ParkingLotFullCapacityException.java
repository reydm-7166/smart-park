package com.smart_park.exceptions.parking;

public class ParkingLotFullCapacityException extends RuntimeException {
    public ParkingLotFullCapacityException(String message) {
        super(message);
    }
}