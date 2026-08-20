package com.smart_park.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.smart_park.domain.ParkingLot;


@Repository
public interface ParkingLotRepository extends CrudRepository<ParkingLot, Long> {
}
