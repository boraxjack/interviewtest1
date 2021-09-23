package com.travix.medusa.busyflights.domain.crazyair;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.junit.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CrazyAirFlightSupplierTest {

    private static final String TEST_URI = "http://localhost";

    @Test
    public void shouldGetResponse() {
        CrazyAirResponse response = new CrazyAirResponse();
        response.setAirline("airline");
        response.setPrice(100);
        response.setDepartureAirportCode("departureCode");
        response.setDestinationAirportCode("destinationCode");
        response.setDepartureDate("2019-01-01T01:02:03");
        response.setArrivalDate("2019-01-01T04:05:06");
        response.setCabinclass("Standard");

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class))).thenReturn(ResponseEntity.ok(response));

        CrazyAirFlightSupplier crazyAirFlightSupplier = new CrazyAirFlightSupplier(restTemplate, TEST_URI);
        BusyFlightsResponse actualResponse = crazyAirFlightSupplier.getFlights("","", LocalDate.of(2020,11,11), LocalDate.of(2020,11,11),4);

        assertEquals(response.getAirline(), actualResponse.getAirline());
        assertEquals(response.getArrivalDate(), actualResponse.getArrivalDateTime().toString());
        assertEquals(response.getDepartureDate(), actualResponse.getDepartDateTime().toString());
        assertEquals(response.getPrice(), actualResponse.getPrice(), 0);
        assertEquals(response.getDepartureAirportCode(), actualResponse.getDepartureAirportCode());
        assertEquals(response.getDestinationAirportCode(), actualResponse.getDestinationAirportCode());
    }

    @Test
    public void shouldThrowException() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class))).thenReturn(ResponseEntity.badRequest().build());

        CrazyAirFlightSupplier crazyAirFlightSupplier = new CrazyAirFlightSupplier(restTemplate, TEST_URI);
        try {
            crazyAirFlightSupplier.getFlights("","", LocalDate.of(2020,11,11), LocalDate.of(2020,11,11),4);
        } catch (RuntimeException e) {
            assertEquals("Failed to post request to CrazyAir API", e.getMessage());
            // I prefer to manually check the exception rather than rely on @Test(expected = RuntimeException.class) because, particularly if the exception isn't specific enough
            // a test can fail for a reason that is different than the expected reason, but still passes
        }
    }
}
