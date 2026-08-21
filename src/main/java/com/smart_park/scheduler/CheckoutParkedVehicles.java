package com.smart_park.scheduler;

import com.smart_park.service.ParkingProcessorService;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckoutParkedVehicles {
    private final  ParkingProcessorService parkingProcessorService;
    public CheckoutParkedVehicles(
            ParkingProcessorService parkingProcessorService
    ) {
        this.parkingProcessorService = parkingProcessorService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void checkoutVehicles() {
        parkingProcessorService.checkoutAutomatically();
    }
}
