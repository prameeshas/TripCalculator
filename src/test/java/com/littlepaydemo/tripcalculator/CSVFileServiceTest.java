package com.littlepaydemo.tripcalculator;

import com.littlepaydemo.tripcalculator.configuration.ApplicationConfiguration;
import com.littlepaydemo.tripcalculator.model.Tap;
import com.littlepaydemo.tripcalculator.model.Trip;
import com.littlepaydemo.tripcalculator.services.OpenCsvFileServiceImpl;
import com.littlepaydemo.tripcalculator.util.enums.Status;
import com.littlepaydemo.tripcalculator.util.enums.Stop;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CSVFileServiceTest {

    @Autowired
    OpenCsvFileServiceImpl openCsvFileService;

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    @Test
    public void csvFileReaderTest() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Taps.csv").getFile());

        List<Tap> taps = openCsvFileService.readFile(file.getAbsolutePath());
        Assert.assertEquals(2, taps.size());

    }

    @Test
    public void fileWriterTest() throws Exception{

        List<Trip> trips = new ArrayList<>();

        trips.add(createTrip( 300l, Stop.STOP1, Stop.STOP2, 3.30, "Company1", "Bus1", "5500005555555559", Status.COMPLETE));
        trips.add(createTrip( 200l, Stop.STOP2, Stop.STOP2, 5.50, "Company2", "Bus2", "5500005555555560", Status.INCOMPLETE));
        trips.add(createTrip( 100l, Stop.STOP3, Stop.STOP3, 0.0, "Company3", "Bus3", "5500005555585559", Status.CANCELLED));

        openCsvFileService.writeFile(applicationConfiguration.getOutputFile(), trips );

        //Check output file content
        File file=new File(applicationConfiguration.getOutputFile());
        FileReader fr=new FileReader(file);
        BufferedReader br=new BufferedReader(fr);

        String line = br.readLine();
        int i = 0;
        while((line=br.readLine())!=null)
        {
            Assert.assertEquals(trips.get(i++).toString(),line);
        }

    }

    public Trip createTrip(Long duration, Stop to, Stop from, double amount, String company, String bus, String pan, Status status){
        Trip trip = new Trip();
        trip.setStarted(LocalDateTime.now());
        trip.setFinished(LocalDateTime.now());
        trip.setDuration(duration);
        trip.setBusId(bus);
        trip.setCompanyId(company);
        trip.setChargeAmt(amount);
        trip.setPan(pan);
        trip.setStatus(status);
        trip.setTo(to);
        trip.setFrom(from);

        return trip;

    }

}
