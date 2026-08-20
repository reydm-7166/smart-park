package com.smart_park.repository;

import com.smart_park.domain.ParkingRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingProcessorRepository extends CrudRepository<ParkingRecord, Long> {
}
