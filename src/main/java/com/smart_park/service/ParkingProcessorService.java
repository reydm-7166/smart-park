package com.smart_park.service;

import com.smart_park.domain.ParkingLot;
import com.smart_park.domain.ParkingRecord;
import com.smart_park.domain.Vehicle;
import com.smart_park.dto.ParkingRecordRequestDTO;
import com.smart_park.exceptions.parking.ParkingLotNotExistingException;
import com.smart_park.exceptions.vehicle.VehicleNotExistingException;
import com.smart_park.repository.ParkingLotRepository;
import com.smart_park.repository.ParkingProcessorRepository;
import com.smart_park.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingProcessorService {
    private final ParkingProcessorRepository parkingProcessorRepo;
    private final VehicleRepository vehicleRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ParkingProcessorService(
            ParkingProcessorRepository parkingProcessorRepo,
            VehicleRepository vehicleRepository,
            ParkingLotRepository parkingLotRepository
    ) {
        this.parkingProcessorRepo = parkingProcessorRepo;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    public void checkIn(ParkingRecordRequestDTO parkingRecord) {

        Vehicle vehicle = vehicleRepository.findById(parkingRecord.getVehicleId()).orElseThrow(
                                                () -> new VehicleNotExistingException("Vehicle not found")
                                            );
        ParkingLot parkingLot = parkingLotRepository.findById(parkingRecord.getParkingId()).orElseThrow(
                                                        () -> new ParkingLotNotExistingException("Parking lot not found")
                                                    );

        // create new parkingRecord and assign the objects values
        ParkingRecord finalParkingRecord = new ParkingRecord();
        finalParkingRecord.setVehicle(vehicle);
        finalParkingRecord.setParking(parkingLot);

        parkingProcessorRepo.save(finalParkingRecord);
    }

    public Iterable<ParkingRecord> getAll() {
        return parkingProcessorRepo.findAll();
    }

    public void checkOut(Long id) {
        parkingLotRepository.findById(id);
    }
}
