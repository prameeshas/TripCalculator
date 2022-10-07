package com.littlepaydemo.tripcalculator;

import com.littlepaydemo.tripcalculator.configuration.ApplicationConfiguration;
import com.littlepaydemo.tripcalculator.configuration.TripFareConfiguration;
import com.littlepaydemo.tripcalculator.model.Tap;
import com.littlepaydemo.tripcalculator.model.Trip;
import com.littlepaydemo.tripcalculator.services.CsvFileService;
import com.littlepaydemo.tripcalculator.services.FareCalculationServiceImpl;
import com.littlepaydemo.tripcalculator.services.TapsProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class TripCalculatorApplication implements CommandLineRunner {

	@Autowired
	private CsvFileService<Trip, Tap> csvFileService;

	@Autowired
	private TapsProcessorService tapsProcessorService;

	@Autowired
	private TripFareConfiguration tripFareConfiguration;

	@Autowired
	private ApplicationConfiguration applicationConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(TripCalculatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0) {
			List<Trip> trips = tapsProcessorService.generateTrips(csvFileService.readFile(args[0]));
			csvFileService.writeFile(applicationConfiguration.getOutputFile(), trips);

		} else {
			log.error("Arguments has not been provided");
		}
	}
}
