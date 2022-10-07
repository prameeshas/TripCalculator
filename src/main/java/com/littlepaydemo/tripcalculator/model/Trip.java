package com.littlepaydemo.tripcalculator.model;

import com.littlepaydemo.tripcalculator.util.enums.Status;
import com.littlepaydemo.tripcalculator.util.enums.Stop;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Slf4j
public class Trip {

    public Trip() {
    }

    public Trip(Tap startTap, Tap endTap, double chargeAmt) {
        this.started = startTap.getDateTimeUtc();
        this.finished = endTap.getDateTimeUtc();
        this.from = startTap.getStopId();
        this.to = endTap.getStopId();

        if(!startTap.getCompanyId().equals(endTap.getCompanyId())){
            log.warn(String.format("Company ID for tap ON and OFF are different for tap IDs %s and  %s", startTap.getId(), endTap.getId()));
        }

        this.companyId = startTap.getCompanyId();


        if(!startTap.getBusId().equals(endTap.getBusId())){
            log.error(String.format("Bus ID for tap ON and OFF are different for tap IDs %s and  %s", startTap.getId(), endTap.getId()));
        }

        this.busId = startTap.getBusId();

        this.pan = endTap.getPan();
        this.status = Status.COMPLETE;
        if(this.from.equals(this.to)) {
            this.status = Status.CANCELLED;
        }
        this.duration = Duration.between(startTap.getDateTimeUtc(), endTap.getDateTimeUtc()).getSeconds();// Duration calculation
        this.chargeAmt = chargeAmt;
    }

    public Trip(Tap startTap, double chargeAmt) {
        // Constructor to capture incomplete trips
        this.started = startTap.getDateTimeUtc();
        this.from = startTap.getStopId();

        this.companyId = startTap.getCompanyId();
        this.busId = startTap.getBusId();

        this.pan = startTap.getPan();
        this.status = Status.INCOMPLETE;
        this.duration = 0l;
        this.chargeAmt = chargeAmt;
    }

    private LocalDateTime started;
    private LocalDateTime finished;
    private Long duration;
    private Stop from;
    private Stop to;
    private double chargeAmt;
    private String companyId;
    private String busId;
    private String pan;
    private Status status;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String toString() {

        String finishedDateTime = "";
        if(finished != null){
            finishedDateTime = finished.format(formatter);
        }
        return
                 started.format(formatter) +
                ", " + finishedDateTime +
                ", " + duration +
                ", " + from +
                ", " + to +
                ", " + chargeAmt +
                ", " + companyId +
                ", " + busId +
                ", " + pan +
                ", " + status;
    }
}
