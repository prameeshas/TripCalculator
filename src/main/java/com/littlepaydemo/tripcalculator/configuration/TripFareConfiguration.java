package com.littlepaydemo.tripcalculator.configuration;

import com.littlepaydemo.tripcalculator.util.enums.Stop;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties("trips-service")
public class TripFareConfiguration {

    private double stop1Stop2;
    private double stop2Stop3;
    private double stop1Stop3;

    List<TripFare> tripFareList = new ArrayList<>();

    public double getStop1Stop2() {
        return stop1Stop2;
    }

    public void setStop1Stop2(double fare) {
        this.stop1Stop2 = fare;
        tripFareList.add(new TripFare(Stop.STOP1, Stop.STOP2, fare));

    }

    public double getStop2Stop3() {
        return stop2Stop3;
    }

    public void setStop2Stop3(double fare) {
        this.stop2Stop3 = fare;
        tripFareList.add(new TripFare(Stop.STOP2, Stop.STOP3, fare));
    }

    public double getStop1Stop3() {
        return stop1Stop3;
    }

    public void setStop1Stop3(double fare) {
        this.stop1Stop3 = fare;
        tripFareList.add(new TripFare(Stop.STOP3, Stop.STOP1, fare));
    }

    public double findFare(Stop start, Stop end){

        List<TripFare> tripFare = tripFareList.stream().filter(s->(s.getStart().equals(start) && s.getEnd().equals(end))
                || s.getStart().equals(end) && s.getEnd().equals(start)).collect(Collectors.toList());

        if(tripFare != null && tripFare.size() == 1){
            return tripFare.get(0).getFare();
        }

        return 0;
    }
}
