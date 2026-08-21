package com.smart_park.dto.parking_process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CheckoutResponse {

    private final Long id;
    private final String plateNumber;
    private final Long lotId;
    private final LocalDateTime checkInTime;
    private final LocalDateTime checkOutTime;
    private final Integer duration;
    private final String fee;

}
