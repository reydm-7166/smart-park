package com.smart_park.controller;

import com.smart_park.domain.ParkingRecord;
import com.smart_park.dto.ParkingRecordRequestDTO;
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
    private void checkIn(@Valid @RequestBody ParkingRecordRequestDTO parkingRecord) {
        parkingProcessorService.checkIn(parkingRecord);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    private Iterable<ParkingRecord> getAll() {
        return parkingProcessorService.getAll();
    }

    @PatchMapping("/{id}/check-out")
    @ResponseStatus(HttpStatus.OK)
    private void checkout(@PathVariable Long id) {
        parkingProcessorService.checkOut(id);
    }
}
