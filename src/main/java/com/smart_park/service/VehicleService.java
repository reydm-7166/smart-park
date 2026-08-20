package com.smart_park.service;

import com.smart_park.domain.Vehicle;
import com.smart_park.exceptions.vehicle.VehicleAlreadyExistsException;
import com.smart_park.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     *
     * @param vehicle vehicle
     */
    public void create(Vehicle vehicle) {
        if (vehicleRepository.existsByPlateNumber(vehicle.getPlateNumber())) {
            throw new VehicleAlreadyExistsException(
                    "Vehicle with the plate number of " + vehicle.getPlateNumber() + " already exists."
            );
        }

        vehicleRepository.save(vehicle);
    }

    public Iterable<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }
}
