package com.smart_park.controller;

import com.smart_park.domain.Vehicle;
import com.smart_park.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) { this.vehicleService = vehicleService; }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Vehicle> getAll() {
        return vehicleService.getAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerVehicle(@Valid @RequestBody Vehicle vehicle) {
        vehicleService.create(vehicle);
    }
}
