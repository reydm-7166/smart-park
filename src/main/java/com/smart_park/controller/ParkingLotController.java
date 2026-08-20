package com.smart_park.controller;


import com.smart_park.domain.ParkingLot;
import com.smart_park.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
