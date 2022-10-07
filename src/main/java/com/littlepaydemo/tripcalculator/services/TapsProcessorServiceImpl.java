package com.littlepaydemo.tripcalculator.services;

import com.littlepaydemo.tripcalculator.model.Tap;
import com.littlepaydemo.tripcalculator.model.Trip;
import com.littlepaydemo.tripcalculator.util.enums.TapType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TapsProcessorServiceImpl implements TapsProcessorService {

    @Autowired
    FareCalculationService fareCalculationService;

    @Override
    public List<Trip> generateTrips(List<Tap> taps) {
        Map<String, Tap> tapMap = new HashMap<>();
        List<Trip> trips = new ArrayList<>();

        //Sorting taps by time
        Collections.sort(taps);

        taps.forEach(tap -> {
            if(tapMap.containsKey(tap.getPan())) {
                Tap prevTap = tapMap.get(tap.getPan());
                if(TapType.ON.equals(prevTap.getTapType())) {
                    if(TapType.OFF.equals(tap.getTapType())) {
                        double fare = fareCalculationService.calculateFare(prevTap.getStopId(), tap.getStopId());
                        var trip = new Trip(prevTap, tap, fare);
                        trips.add(trip);
                        tapMap.remove(tap.getPan());

                    } else {
                        //Handling invalid tap ON records
                        log.error("INVALID TAP ON, already contains an ON record for tap ID ", tap.getId());

                        double fare = fareCalculationService.calculateFare(prevTap.getStopId(), null);
                        var trip = new Trip(prevTap, fare);
                        trips.add(trip);
                        tapMap.put(tap.getPan(), tap);
                    }
                }
            } else {
                //Handling invalid tap OFF records
                if(TapType.OFF.equals(tap.getTapType())) {
                    log.error("INVALID TAP OUT, no tap ON found for tap ID ", tap.getId());
                } else {
                    tapMap.put(tap.getPan(), tap);
                }
            }
        });

        //capture all remaining incomplete trips
        tapMap.values().forEach(tap -> {
            trips.add(new Trip(tap, fareCalculationService.calculateFare(tap.getStopId(), null)));
        });

        return trips;
    }
}
