package com.smart_park.dto.parking_lot;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ParkingLotResponse {
    private String location;
    private Integer capacity;
    private Integer costPerMin;
    private Long occupiedSpaces;
}
