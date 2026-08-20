package com.smart_park.service;

import com.smart_park.domain.ParkingLot;
import com.smart_park.domain.ParkingRecord;
import com.smart_park.domain.Vehicle;
import com.smart_park.dto.ParkingRecordRequestDTO;
import com.smart_park.exceptions.parking.ParkingLotNotExistingException;
import com.smart_park.exceptions.record.ParkingRecordNotExistingException;
import com.smart_park.exceptions.vehicle.VehicleNotExistingException;
import com.smart_park.repository.ParkingLotRepository;
import com.smart_park.repository.ParkingProcessorRepository;
import com.smart_park.repository.VehicleRepository;
import com.smart_park.util.ParkingFeeCalculatorUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        Vehicle vehicle =  vehicleRepository.findById(parkingRecord.getVehicleId())
                                            .orElseThrow(
                                                    () -> new VehicleNotExistingException("Vehicle not found")
                                            );
        ParkingLot parkingLot = parkingLotRepository.findById(parkingRecord.getParkingId())
                                            .orElseThrow(
                                                    () -> new ParkingLotNotExistingException("Parking lot not found")
                                            );

        // add checking to check if vehicle is already parked somewhere
        // scan the whole parking record see if there is any isActive with its plate number or vehicle id

        // create new parkingRecord and assign the objects values
        ParkingRecord finalParkingRecord = new ParkingRecord();
        finalParkingRecord.setVehicle(vehicle);
        finalParkingRecord.setParking(parkingLot);

        parkingProcessorRepo.save(finalParkingRecord);
    }

    public Iterable<ParkingRecord> getAll() {
        return parkingProcessorRepo.findAll();
    }

    public ResponseEntity<> checkOut(Long id) {
        ParkingRecord parkingRecord = parkingProcessorRepo.findById(id)
                                            .orElseThrow(
                                                    () -> new ParkingRecordNotExistingException("This vehicle is not parked here.")
                                            );

        Integer parkingCostPerMinute = parkingRecord.getParking().getCostPerMin();
        Integer parkingDuration = ParkingFeeCalculatorUtil.getTotalDurationInMinutes(parkingRecord.getCreated_at());

        parkingRecord.setIsActive(false);
        parkingProcessorRepo.save(parkingRecord);
        String fee = ParkingFeeCalculatorUtil.getTotalFee(parkingDuration, parkingCostPerMinute);
    }

}
