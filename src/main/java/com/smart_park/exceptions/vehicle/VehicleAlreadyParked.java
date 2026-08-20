package com.smart_park.exceptions.vehicle;

public class VehicleAlreadyParked extends RuntimeException {
    public VehicleAlreadyParked(String message) {
        super(message);
    }
}
