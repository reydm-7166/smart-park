package com.smart_park.controller;


import com.smart_park.domain.ParkingLot;
import com.smart_park.dto.parking_lot.CheckVehiclesParkInLotResponse;
import com.smart_park.dto.vehicles.VehiclesParkedResponse;
import com.smart_park.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/parking-lot")
public class ParkingLotController {
    private final ParkingService parkingService;

    public ParkingLotController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    private Iterable<ParkingLot> getParking() {
        return parkingService.getAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    private void create(@Valid @RequestBody ParkingLot parkingLot) {
        parkingService.create(parkingLot);
    }

    @GetMapping("/{id}/currently-parked")
    @ResponseStatus(HttpStatus.OK)
    private List<VehiclesParkedResponse> checkParkedVehicles(@PathVariable Long id) {
        return parkingService.checkParkedVehicles(id);
    }
}
