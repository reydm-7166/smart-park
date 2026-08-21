package com.smart_park.dto.parking_process;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckinRequest
{
    // getters and setters
    // getters and setters
    @NotNull(message = "Vehicle plateNumber is required.")
    private Long vehicleId;

    @NotNull(message = "Parking code is required.")
    private Long parkingId;

}
