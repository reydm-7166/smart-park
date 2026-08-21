package com.smart_park.service;

import com.smart_park.domain.ParkingLot;
import com.smart_park.domain.ParkingRecord;
import com.smart_park.domain.Vehicle;
import com.smart_park.dto.parking_process.CheckAvailableParkingSpaceResponse;
import com.smart_park.dto.parking_process.CheckoutResponse;
import com.smart_park.dto.parking_process.CheckinRequest;
import com.smart_park.exceptions.parking.ParkingLotFullCapacityException;
import com.smart_park.exceptions.parking.ParkingLotNotExistingException;
import com.smart_park.exceptions.record.ParkingRecordAlreadyCheckoutException;
import com.smart_park.exceptions.record.ParkingRecordNotExistingException;
import com.smart_park.exceptions.record.VehicleAlreadyParkedException;
import com.smart_park.exceptions.vehicle.VehicleNotExistingException;
import com.smart_park.repository.ParkingLotRepository;
import com.smart_park.repository.ParkingProcessorRepository;
import com.smart_park.repository.VehicleRepository;
import com.smart_park.util.ParkingFeeCalculatorUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public void checkIn(CheckinRequest parkingRecord) throws VehicleAlreadyParkedException {

        Long vehicleId = parkingRecord.getVehicleId();
        Long parkingId = parkingRecord.getParkingId();
        Vehicle vehicle =  vehicleRepository.findById(vehicleId)
                                            .orElseThrow(
                                                    () -> new VehicleNotExistingException("Vehicle not found")
                                            );
        ParkingLot parkingLot = parkingLotRepository.findById(parkingId)
                                            .orElseThrow(
                                                    () -> new ParkingLotNotExistingException("Parking lot not found")
                                            );

        // get all parked in the requested lot
        Integer parkedInLotCount = parkingProcessorRepo.getParkedCountByLotId(parkingId);
        // add checking to check if vehicle is already parked somewhere
        Integer isVehicleParkedSomewhere = parkingProcessorRepo.checkIfParkedAlready(vehicleId);
        if (isVehicleParkedSomewhere > 0) {
            throw new VehicleAlreadyParkedException("This vehicle is already parked somewhere. Please Checkout first!");
        }

        // check if there is vacant parking space
        if (parkedInLotCount >= parkingLot.getCapacity()) {
            throw new ParkingLotFullCapacityException("Parking spaces for this lot is already at full capacity");
        }

        // create new parkingRecord and assign the objects values
        ParkingRecord finalParkingRecord = new ParkingRecord();
        finalParkingRecord.setVehicle(vehicle);
        finalParkingRecord.setParking(parkingLot);
        finalParkingRecord.setIsActive(true);

        parkingProcessorRepo.save(finalParkingRecord);
    }

    public Iterable<ParkingRecord> getAll() {
        return parkingProcessorRepo.findAll();
    }

    public CheckoutResponse checkOut(Long id) {
        ParkingRecord parkingRecord = parkingProcessorRepo.findById(id)
                                            .orElseThrow(
                                                    () -> new ParkingRecordNotExistingException("This vehicle is not parked here.")
                                            );

        if (!parkingRecord.getIsActive()) {
            throw new ParkingRecordAlreadyCheckoutException("Vehicle already checkout here.");
        }

        Integer parkingCostPerMinute = parkingRecord.getParking().getCostPerMin();
        Integer parkingDuration = ParkingFeeCalculatorUtil.getTotalDurationInMinutes(parkingRecord.getCreated_at());

        parkingRecord.setIsActive(false);
        parkingProcessorRepo.save(parkingRecord);
        String fee = ParkingFeeCalculatorUtil.getTotalFee(parkingDuration, parkingCostPerMinute);

        return new CheckoutResponse(
                parkingRecord.getId(),
                parkingRecord.getVehicle().getPlateNumber(),
                parkingRecord.getParking().getId(),
                parkingRecord.getCreated_at(),
                parkingRecord.getUpdated_at(),
                parkingDuration,
                fee
        );
    }

    /**
     *
     *
     *
     * @param id
     * @return CheckAvailableParkingSpaceResponse
     */
    public CheckAvailableParkingSpaceResponse checkAvailable(Long id) {
        // SELECT COUNT(*) from parking_record WHERE parking_id is equal to {id} AND parking_record.isActive = true;
        // then get parking_lot capacity subtract to query result, return and format.
        // if max capacity return vacancy: none, next_available: dateTime of oldest checkedIn/created_at record
        Integer parkedInCount = parkingProcessorRepo.countCurrentlyParked(id);
        LocalDateTime oldestParked = parkingProcessorRepo.getOldestParked(id).orElse(null);
        Integer parkingLotCapacity = parkingLotRepository.findById(id)
                                            .orElseThrow(
                                                () -> new ParkingLotNotExistingException("Parking lot not existing")
                                            )
                                            .getCapacity();

        Integer vacancy = parkingLotCapacity - parkedInCount;

        return new CheckAvailableParkingSpaceResponse(
                parkedInCount,
                vacancy,
                oldestParked
        );
    }

    /**
     * This checks for PARKED vehicles longer than 15 mins (gets called every minute)
     *
     * @return void
     */
    public void checkoutAutomatically() {

        System.out.println("Checkout automatically scanning vehicles parked for more than 15 mins ... ");

        LocalDateTime cutOff = LocalDateTime.now().minusMinutes(1);
        Iterable<ParkingRecord> parkRecords = parkingProcessorRepo.checkAllParkedRecords(cutOff);

        for (ParkingRecord parkedVehicle : parkRecords) {
            System.out.println("ParkingProcessorService, Parked vehicle id " + parkedVehicle.getId());
            // call the checkout Method in the service
            CheckoutResponse result = this.checkOut(parkedVehicle.getId());

            // this is a cron job so ilog na lang ung checkout receipt/response
            // usually this will be emailed and put to queue but test lang naman to
            System.out.println("ParkingProcessorService, Parking receipt of vehicle " + parkedVehicle.getVehicle().getPlateNumber() + " has been checkout." + result.toString());
        }
    }

}
