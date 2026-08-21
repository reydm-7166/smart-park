package com.smart_park.repository;

import com.smart_park.domain.ParkingRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ParkingProcessorRepository extends CrudRepository<ParkingRecord, Long> {

    @Query("SELECT COUNT(*) from ParkingRecord pk WHERE pk.parking.id = :id AND pk.isActive = true")
    Integer countCurrentlyParked(@Param("id") Long id);

    @Query("SELECT MIN(pk.created_at) from ParkingRecord pk WHERE pk.parking.id = :id AND pk.isActive = true")
    Optional<LocalDateTime> getOldestParked(@Param("id") Long id);

    @Query("SELECT COUNT(pk.vehicle.id) from ParkingRecord pk WHERE pk.vehicle.id = :id AND pk.isActive = true")
    Integer checkIfParkedAlready(@Param("id") Long id);

    @Query("SELECT COUNT(pk.id) from ParkingRecord pk WHERE pk.parking.id = :id AND pk.isActive = true")
    Integer getParkedCountByLotId(@Param("id") Long id);
}
