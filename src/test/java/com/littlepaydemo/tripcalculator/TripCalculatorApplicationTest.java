package com.littlepaydemo.tripcalculator;

import com.littlepaydemo.tripcalculator.model.Tap;
import com.littlepaydemo.tripcalculator.model.Trip;
import com.littlepaydemo.tripcalculator.services.FareCalculationService;
import com.littlepaydemo.tripcalculator.services.TapsProcessorServiceImpl;
import com.littlepaydemo.tripcalculator.util.enums.Status;
import com.littlepaydemo.tripcalculator.util.enums.Stop;
import com.littlepaydemo.tripcalculator.util.enums.TapType;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class TripCalculatorApplicationTest {

	List<Tap> taps = new ArrayList<>();

	@InjectMocks
	TapsProcessorServiceImpl tapsProcessorService;

	@Mock
	FareCalculationService fareCalculationService;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		Mockito.when(fareCalculationService.calculateFare(any(), any())).thenReturn(1.0d);

	}

	@Test
	void incompleteTripForAllONTapTypeTest() {

		taps.add(createTap("1", LocalDateTime.now(), TapType.ON, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("2", LocalDateTime.now().minusSeconds(2), TapType.ON, Stop.STOP2, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("3", LocalDateTime.now().minusSeconds(3), TapType.ON, Stop.STOP3, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("4", LocalDateTime.now().minusSeconds(4), TapType.ON, Stop.STOP3, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("5", LocalDateTime.now().minusSeconds(5), TapType.ON, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));

		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(5, trips.size());

		for (Trip trip:trips) {
			Assert.assertEquals(Status.INCOMPLETE,trip.getStatus());
			Assert.assertEquals(0l,trip.getDuration().longValue());
			Assert.assertEquals(1.0d,trip.getChargeAmt(), 0.0000f);
			Assert.assertEquals("5500005555555559",trip.getPan());
			Assert.assertEquals("Company1",trip.getCompanyId());
			Assert.assertEquals("Bus1",trip.getBusId());
		}

	}

	@Test
	void incompleteTripForAllOFFTapTypeTest() {

		taps.add(createTap("1", LocalDateTime.now(), TapType.OFF, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("2", LocalDateTime.now().minusSeconds(2), TapType.OFF, Stop.STOP2, "Company1", "Bus1", "5500005555555559" ));

		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(0, trips.size());

	}

	@Test
	void completeTripTest() {

		taps.add(createTap("1", LocalDateTime.parse("06-10-2022 13:00:03", formatter), TapType.OFF, Stop.STOP1, "Company1", "Bus2", "5500005555555559" ));
		taps.add(createTap("2",  LocalDateTime.parse("06-10-2022 13:00:00", formatter), TapType.ON, Stop.STOP2, "Company1", "Bus2", "5500005555555559" ));

		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(1, trips.size());

		for (Trip trip:trips) {
			Assert.assertEquals(Status.COMPLETE,trip.getStatus());
			Assert.assertEquals(3l,trip.getDuration().longValue());
			Assert.assertEquals(1.0d,trip.getChargeAmt(), 0.0000f);
			Assert.assertEquals("5500005555555559",trip.getPan());
			Assert.assertEquals("Company1",trip.getCompanyId());
			Assert.assertEquals("Bus2",trip.getBusId());
		}

	}

	@Test
	void completeTripDifferentCompanyIDTest() {

		taps.add(createTap("1", LocalDateTime.parse("06-10-2022 13:00:03", formatter), TapType.OFF, Stop.STOP1, "Company1", "Bus2", "5500005555555559" ));
		taps.add(createTap("2",  LocalDateTime.parse("06-10-2022 13:00:00", formatter), TapType.ON, Stop.STOP2, "Company2", "Bus2", "5500005555555559" ));

		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(1, trips.size());

	}

	@Test
	void completeTripDifferentBusIDTest() {

		taps.add(createTap("1", LocalDateTime.parse("06-10-2022 13:00:03", formatter), TapType.OFF, Stop.STOP1, "Company1", "Bus2", "5500005555555559" ));
		taps.add(createTap("2",  LocalDateTime.parse("06-10-2022 13:00:00", formatter), TapType.ON, Stop.STOP2, "Company1", "Bus1", "5500005555555559" ));

		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(1, trips.size());

	}

	@Test
	void completeAndIncompleteTripTest() {

		taps.add(createTap("1", LocalDateTime.parse("06-10-2022 13:00:03", formatter), TapType.OFF, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("2", LocalDateTime.parse("06-10-2022 13:00:00", formatter), TapType.ON, Stop.STOP2, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("3", LocalDateTime.parse("06-10-2022 13:00:02", formatter), TapType.ON, Stop.STOP2, "Company1", "Bus1", "5500005555555558" ));

		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(2, trips.size());

		Assert.assertEquals(Status.COMPLETE,trips.get(0).getStatus());
		Assert.assertEquals(Status.INCOMPLETE,trips.get(1).getStatus());
		Assert.assertEquals(3,trips.get(0).getDuration().longValue());

	}

	@Test
	void cancelledTripTest() {

		taps.add(createTap("1", LocalDateTime.parse("06-10-2022 13:00:03", formatter), TapType.OFF, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("2", LocalDateTime.parse("06-10-2022 13:00:00", formatter), TapType.ON, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));

		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(1, trips.size());

		Assert.assertEquals(Status.CANCELLED, trips.get(0).getStatus());
		Assert.assertEquals(3l, trips.get(0).getDuration().longValue());

	}

	@Test
	void allTripTypesTest() {
		// Testing all possible combination of status - COMPLETE, INCOMPLETE, CANCELLED
		taps.add(createTap("1", LocalDateTime.parse("06-10-2022 13:00:03", formatter), TapType.OFF, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("2", LocalDateTime.parse("06-10-2022 13:00:00", formatter), TapType.ON, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("3", LocalDateTime.parse("06-10-2022 13:00:04", formatter), TapType.OFF, Stop.STOP1, "Company2", "Bus1", "5500005555555557" ));
		taps.add(createTap("4", LocalDateTime.parse("06-10-2022 13:00:03", formatter), TapType.ON, Stop.STOP2, "Company2", "Bus1", "5500005555555557" ));
		taps.add(createTap("5", LocalDateTime.parse("06-10-2022 13:00:05", formatter), TapType.ON, Stop.STOP2, "Company1", "Bus1", "5500005555555558" ));

		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(3, trips.size());

		Assert.assertEquals(Status.CANCELLED, trips.get(0).getStatus());
		Assert.assertEquals(Status.COMPLETE, trips.get(1).getStatus());
		Assert.assertEquals(Status.INCOMPLETE, trips.get(2).getStatus());
		Assert.assertEquals(3l, trips.get(0).getDuration().longValue());

	}

	@Test
	void completedTripsForTheSameUser() {
		taps.add(createTap("1", LocalDateTime.parse("06-10-2022 13:00:02", formatter), TapType.OFF, Stop.STOP1, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("2", LocalDateTime.parse("06-10-2022 13:00:01", formatter), TapType.ON, Stop.STOP2, "Company1", "Bus1", "5500005555555559" ));
		taps.add(createTap("3", LocalDateTime.parse("06-10-2022 13:00:04", formatter), TapType.OFF, Stop.STOP3, "Company1", "Bus2", "5500005555555559" ));
		taps.add(createTap("4", LocalDateTime.parse("06-10-2022 13:00:03", formatter), TapType.ON, Stop.STOP2, "Company1", "Bus2", "5500005555555559" ));


		List<Trip> trips = tapsProcessorService.generateTrips(taps);
		Assert.assertEquals(2, trips.size());

		Assert.assertEquals(Status.COMPLETE, trips.get(0).getStatus());
		Assert.assertEquals(Status.COMPLETE, trips.get(1).getStatus());
		Assert.assertEquals(1l, trips.get(0).getDuration().longValue());
		Assert.assertEquals(1l, trips.get(1).getDuration().longValue());

	}

	private Tap createTap(String id, LocalDateTime time, TapType tapType, Stop stop, String company, String bus, String pan){
		Tap tap = new Tap();
		tap.setId(id);
		tap.setDateTimeUtc(time);
		tap.setTapType(tapType);
		tap.setStopId(stop);
		tap.setCompanyId(company);
		tap.setBusId(bus);
		tap.setPan(pan);

		return tap;
	}

}
