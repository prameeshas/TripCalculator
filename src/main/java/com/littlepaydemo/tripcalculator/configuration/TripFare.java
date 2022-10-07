package com.littlepaydemo.tripcalculator.configuration;

import com.littlepaydemo.tripcalculator.util.enums.Stop;
import lombok.Data;

@Data
public  class TripFare {
        private Stop start;
        private Stop end;
        private double fare;

        public TripFare(Stop start, Stop end, double fare) {
            this.start = start;
            this.end = end;
            this.fare = fare;
        }

}
