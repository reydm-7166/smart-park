package com.smart_park.dto.parking_process;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckAvailableParkingSpaceResponse {

    private Integer occupied;
    private Integer vacant;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime next_available;
}
