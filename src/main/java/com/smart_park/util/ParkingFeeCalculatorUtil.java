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

    public static Integer getTotalDurationInMinutes(LocalDateTime start) {
        return Math.toIntExact(Duration.between(start, LocalDateTime.now()).toMinutes());
    }
}
