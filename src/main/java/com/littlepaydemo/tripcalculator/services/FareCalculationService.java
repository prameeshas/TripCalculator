package com.littlepaydemo.tripcalculator.services;

import com.littlepaydemo.tripcalculator.util.enums.Stop;

public interface FareCalculationService {
    double calculateFare(Stop from, Stop to);
}
