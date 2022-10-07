package com.littlepaydemo.tripcalculator.services;

import com.littlepaydemo.tripcalculator.configuration.TripFareConfiguration;
import com.littlepaydemo.tripcalculator.util.enums.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FareCalculationServiceImpl implements FareCalculationService {

    public static final String STOP = "STOP";

    @Autowired
    public TripFareConfiguration tripFare;

    private int maxStop = 3;

    private FareCalculationServiceImpl() {
    }

    @Override
    public double calculateFare(Stop from, Stop to) {

        double fare = 0;

        Stop end1;
        Stop end2;

        if( to == null){
            if(from == Stop.STOP1){
                end1 = Stop.valueOf(STOP + (maxStop - 1));
                end2 = Stop.valueOf(STOP + (maxStop));

            }else if (from == Stop.STOP2){
                end1 = Stop.valueOf(STOP + (maxStop - 1));
                end2 = Stop.valueOf(STOP + (maxStop  ));

            }else{
                end1 = Stop.valueOf(STOP + (maxStop-1));
                end2 = Stop.valueOf(STOP + (maxStop-2));
            }

            double fare1 = tripFare.findFare(from, end1);
            double fare2 = tripFare.findFare(from, end2);

            fare = fare1>fare2 ? fare1 :fare2;

        }else if(to.equals(from)){
            fare = 0;
        }else{
            fare = tripFare.findFare(to, from);
        }

        return fare;
    }
}
