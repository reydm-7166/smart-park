package com.smart_park.repository;

import com.smart_park.domain.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    boolean existsByPlateNumber(String plateNumber);
}
