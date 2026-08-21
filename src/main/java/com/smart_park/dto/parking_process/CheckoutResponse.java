package com.smart_park.dto.parking_process;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CheckoutResponse {

    private final Long id;
    private final String plateNumber;
    private final Long lotId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime checkInTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime checkOutTime;

    private final Integer duration;
    private final String fee;

}
