package com.smart_park.service;

import com.smart_park.domain.ParkingLot;
import com.smart_park.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {
    private final ParkingLotRepository parkingRepo;

    public ParkingService(ParkingLotRepository parkingRepo) {
        this.parkingRepo = parkingRepo;
    }

    public Iterable<ParkingLot> getAll() {
        return parkingRepo.findAll();
    }

    public boolean create(ParkingLot parkingLot) {
        parkingRepo.save(parkingLot);
        return true;
    }
}
