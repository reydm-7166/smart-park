package com.smart_park.util;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;


public class ParkingFeeCalculatorUtil {

    public static String getTotalFee(Integer duration, Integer costPerMin) {
        Integer totalFee = duration * costPerMin;

        DecimalFormat decimal = new DecimalFormat("#,##0.00");
        return decimal.format(totalFee);
    }

    /**
     * Calculate the customers stay, minimum is 3 mins.
     *
     * @return Integer
     */
    public static Integer getTotalDurationInMinutes(LocalDateTime start) {
        int total = Math.toIntExact(Duration.between(start, LocalDateTime.now()).toMinutes());

        return Math.max(total, 3);
    }
}
