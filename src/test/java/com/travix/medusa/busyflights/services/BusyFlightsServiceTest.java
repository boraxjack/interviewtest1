package com.travix.medusa.busyflights.services;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplier;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class BusyFlightsServiceTest {

    @Test
    public void shouldReturnFlight() {
        FlightSupplier supplier = mock(FlightSupplier.class);
        BusyFlightsService service = new BusyFlightsService(Collections.singletonList(supplier));

        BusyFlightsResponse response = new BusyFlightsResponse()
                .setAirline("airline")
                .setDepartDateTime(LocalDateTime.of(2020, 1, 1, 12, 0))
                .setArrivalDateTime(LocalDateTime.of(2020, 1, 1, 18, 0))
                .setSupplier("supplier")
                .setDepartureAirportCode("departureCode")
                .setDestinationAirportCode("destinationCode")
                .setPrice(100);

        BusyFlightsRequest request = new BusyFlightsRequest();
        request.setDepartureDate(LocalDate.of(2020, 1, 1).toString());
        request.setReturnDate(LocalDate.of(2020, 1, 1).toString());
        request.setOrigin("departureCode");
        request.setDestination("destinationCode");
        request.setNumberOfPassengers(4);

        when(supplier.getFlights("departureCode", "destinationCode", LocalDate.of(2020,1,1), LocalDate.of(2020,1,1), 4))
                .thenReturn(response);

        List<BusyFlightsResponse> actualResponse = service.getFlights(request);
        assertEquals(1, actualResponse.size());
        BusyFlightsResponse r = actualResponse.get(0);
        assertEquals(response, r);
    }

    @Test
    public void shouldReturnFlights() {
        FlightSupplier supplier1 = mock(FlightSupplier.class);
        FlightSupplier supplier2 = mock(FlightSupplier.class);
        BusyFlightsService service = new BusyFlightsService(asList(supplier1, supplier2));

        BusyFlightsResponse response1 = new BusyFlightsResponse()
                .setAirline("airline")
                .setDepartDateTime(LocalDateTime.of(2020, 1, 1, 12, 0))
                .setArrivalDateTime(LocalDateTime.of(2020, 1, 1, 18, 0))
                .setSupplier("supplier")
                .setDepartureAirportCode("departureCode")
                .setDestinationAirportCode("destinationCode")
                .setPrice(100);

        BusyFlightsResponse response2 = new BusyFlightsResponse()
                .setAirline("airline2")
                .setDepartDateTime(LocalDateTime.of(2020, 1, 1, 9, 30))
                .setArrivalDateTime(LocalDateTime.of(2020, 1, 1, 16, 45))
                .setSupplier("supplier")
                .setDepartureAirportCode("departureCode")
                .setDestinationAirportCode("destinationCode")
                .setPrice(100);

        BusyFlightsRequest request = new BusyFlightsRequest();
        request.setDepartureDate(LocalDate.of(2020, 1, 1).toString());
        request.setReturnDate(LocalDate.of(2020, 1, 1).toString());
        request.setOrigin("departureCode");
        request.setDestination("destinationCode");
        request.setNumberOfPassengers(4);

        when(supplier1.getFlights("departureCode", "destinationCode", LocalDate.of(2020,1,1), LocalDate.of(2020,1,1), 4))
                .thenReturn(response1);
        when(supplier2.getFlights("departureCode", "destinationCode", LocalDate.of(2020,1,1), LocalDate.of(2020,1,1), 4))
                .thenReturn(response2);

        List<BusyFlightsResponse> actualResponse = service.getFlights(request);
        assertEquals(2, actualResponse.size());
        BusyFlightsResponse r = actualResponse.get(0);
        assertThat(actualResponse, containsInAnyOrder(response1, response2));
    }

    @Test
    public void shouldReturnNoResults() {
        FlightSupplier supplier = mock(FlightSupplier.class);
        BusyFlightsService service = new BusyFlightsService(Collections.singletonList(supplier));
        BusyFlightsRequest request = new BusyFlightsRequest();
        request.setDepartureDate(LocalDate.of(2020, 1, 1).toString());
        request.setReturnDate(LocalDate.of(2020, 1, 1).toString());
        request.setOrigin("departureCode");
        request.setDestination("destinationCode");
        request.setNumberOfPassengers(4);

        List<BusyFlightsResponse> actualResponse = service.getFlights(request);
        assertEquals(0, actualResponse.size());
    }
}