package com.smart_park.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public class ParkingRecordRequestDTO
{
    // getters and setters
    @NotNull(message = "Vehicle plateNumber is required.")
    private Long vehicleId;

    @NotNull(message = "Parking code is required.")
    private Long parkingId;

    // getters and setters
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public Long getParkingId() { return parkingId; }
    public void setParkingId(Long parkingId) { this.parkingId = parkingId; }
}
