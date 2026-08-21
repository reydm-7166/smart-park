package com.smart_park.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.smart_park.domain.ParkingLot;

import java.util.List;


@Repository
public interface ParkingLotRepository extends CrudRepository<ParkingLot, Long> {
    @Query("""
        SELECT pl, COUNT(pr)
        FROM ParkingLot pl
        LEFT JOIN ParkingRecord pr
            ON pr.parking.id = pl.id
            AND pr.isActive = true
        GROUP BY pl
    """)
    List<Object[]> findAllWithOccupiedSpaces();
}
