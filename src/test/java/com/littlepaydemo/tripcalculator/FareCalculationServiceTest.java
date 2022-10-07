package com.littlepaydemo.tripcalculator;

import com.littlepaydemo.tripcalculator.services.FareCalculationService;
import com.littlepaydemo.tripcalculator.util.enums.Stop;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FareCalculationServiceTest {

    @Autowired
    private FareCalculationService fareCalculationService;

    @Test
    public void calculateFareForCompletedTripTest(){
        Assert.assertEquals(7.3d, fareCalculationService.calculateFare(Stop.STOP1, Stop.STOP3), 0.00000);
    }

    @Test
    public void calculateFareForIncompletedTripTest(){
        Assert.assertEquals(7.3d, fareCalculationService.calculateFare(Stop.STOP1, null), 0.00000);
    }

    @Test
    public void calculateFareForIncompletedTripStop1Test(){
        Assert.assertEquals(7.3d, fareCalculationService.calculateFare(Stop.STOP1, null), 0.00000);
    }

    @Test
    public void calculateFareForIncompletedTripStop2Test(){
        Assert.assertEquals(5.5d, fareCalculationService.calculateFare(Stop.STOP2, null), 0.00000);
    }

    @Test
    public void calculateFareForCancelledTripTest(){
        Assert.assertEquals(0.0d, fareCalculationService.calculateFare(Stop.STOP2, Stop.STOP2), 0.00000);
    }
}
