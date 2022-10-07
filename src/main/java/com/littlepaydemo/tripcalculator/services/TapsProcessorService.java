package com.littlepaydemo.tripcalculator.services;

import com.littlepaydemo.tripcalculator.model.Tap;
import com.littlepaydemo.tripcalculator.model.Trip;

import java.util.List;

public interface TapsProcessorService {
    List<Trip> generateTrips(List<Tap> taps);
}
