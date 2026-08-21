package com.smart_park.repository;

import com.smart_park.domain.ParkingRecord;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingProcessorRepository extends CrudRepository<ParkingRecord, Long> {

    @Query("SELECT COUNT(*) from ParkingRecord pk WHERE pk.parking.id = :id AND pk.isActive = true")
    Integer countCurrentlyParked(@Param("id") Long id);

    @Query("SELECT MIN(pk.created_at) FROM ParkingRecord pk WHERE pk.parking.id = :id AND pk.isActive = true")
    Optional<LocalDateTime> getOldestParked(@Param("id") Long id);

    @Query("SELECT COUNT(pk.vehicle.id) FROM ParkingRecord pk WHERE pk.vehicle.id = :id AND pk.isActive = true")
    Integer checkIfParkedAlready(@Param("id") Long id);

    @Query("SELECT COUNT(pk.id) FROM ParkingRecord pk WHERE pk.parking.id = :id AND pk.isActive = true")
    Integer getParkedCountByLotId(@Param("id") Long id);

    @Query("SELECT pk FROM ParkingRecord pk WHERE pk.created_at <= :cutOff AND pk.isActive = true")
    Iterable<ParkingRecord> checkAllParkedRecords(@Param("cutOff") LocalDateTime cutOff);
}
