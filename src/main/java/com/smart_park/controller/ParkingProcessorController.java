package com.smart_park.controller;

import com.smart_park.domain.ParkingRecord;
import com.smart_park.dto.parking_process.CheckAvailableParkingSpaceResponse;
import com.smart_park.dto.parking_process.CheckoutResponse;
import com.smart_park.dto.parking_process.CheckinRequest;
import com.smart_park.service.ParkingProcessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/smart-park")
public class ParkingProcessorController {

    private final ParkingProcessorService parkingProcessorService;

    public ParkingProcessorController(ParkingProcessorService parkingProcessorService) {
        this.parkingProcessorService = parkingProcessorService;
    }

    @PostMapping("/check-in")
    @ResponseStatus(HttpStatus.CREATED)
    private void checkIn(@Valid @RequestBody CheckinRequest parkingRecord) {
        parkingProcessorService.checkIn(parkingRecord);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    private Iterable<ParkingRecord> getAll() {
        return parkingProcessorService.getAll();
    }

    @PatchMapping("/{id}/check-out")
    @ResponseStatus(HttpStatus.OK)
    private CheckoutResponse checkout(@PathVariable Long id) {
        return parkingProcessorService.checkOut(id);
    }

    @GetMapping("/{id}/check-available")
    private CheckAvailableParkingSpaceResponse checkAvailable(@PathVariable Long id) {
        return parkingProcessorService.checkAvailable(id);
    }
}
