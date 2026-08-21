package com.smart_park.service;

import com.smart_park.domain.ParkingLot;
import com.smart_park.domain.ParkingRecord;
import com.smart_park.dto.parking_lot.ParkingLotResponse;
import com.smart_park.dto.vehicles.VehiclesParkedResponse;
import com.smart_park.exceptions.parking.ParkingLotNotExistingException;
import com.smart_park.repository.ParkingLotRepository;
import com.smart_park.repository.ParkingProcessorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingService {
    private final ParkingLotRepository parkingRepo;
    private final ParkingProcessorRepository parkingProcessorRepo;

    public ParkingService(
            ParkingLotRepository parkingRepo,
            ParkingProcessorRepository parkingProcessorRepository
    ) {
        this.parkingRepo = parkingRepo;
        this.parkingProcessorRepo = parkingProcessorRepository;
    }

    public Iterable<ParkingLotResponse> getAll() {
        return parkingRepo.findAllWithOccupiedSpaces()
                .stream()
                .map(row -> {
                    ParkingLot parkingLot = (ParkingLot) row[0];
                    long occupiedSpaces = (Long) row[1];

                    return new ParkingLotResponse(
                            parkingLot.getLocation(),
                            parkingLot.getCapacity(),
                            parkingLot.getCostPerMin(),
                            occupiedSpaces
                    );
                })
                .toList();
    }

    public boolean create(ParkingLot parkingLot) {
        parkingRepo.save(parkingLot);
        return true;
    }

    public List<VehiclesParkedResponse> checkParkedVehicles(Long id) {
        ParkingLot parkingLot = parkingRepo.findById(id)
                .orElseThrow(
                        () -> new ParkingLotNotExistingException("Parking lot not found")
                );
        Iterable<ParkingRecord> parkedVehiclesList = parkingProcessorRepo.checkParkedVehicles(id);

        List<VehiclesParkedResponse> parkedVehicleList = new ArrayList<>();
        for (ParkingRecord parkedVehicle : parkedVehiclesList) {
            VehiclesParkedResponse response = new VehiclesParkedResponse();

            response.setPlateNumber(parkedVehicle.getVehicle().getPlateNumber());
            response.setType(parkedVehicle.getVehicle().getType());
            response.setName(parkedVehicle.getVehicle().getName());
            response.setParkedAt(parkedVehicle.getCreated_at());
            parkedVehicleList.add(response);
        }
        return parkedVehicleList;
    }
}
