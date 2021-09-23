package com.travix.medusa.busyflights.domain.toughjet;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirFlightSupplier;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.junit.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ToughJetFlightSupplierTest {

    private static final String TEST_URI = "http://localhost";

    @Test
    public void shouldGetResponse() {
        ToughJetResponse response = new ToughJetResponse();
        response.setCarrier("airline");
        response.setBasePrice(100);
        response.setDiscount(20);
        response.setTax(10);
        response.setDepartureAirportName("departureCode");
        response.setDepartureAirportName("destinationCode");
        response.setOutboundDateTime("2019-01-01T01:02:03");
        response.setInboundDateTime("2019-01-01T04:05:06");

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class))).thenReturn(ResponseEntity.ok(response));

        ToughJetFlightSupplier toughJetFlightSupplier = new ToughJetFlightSupplier(restTemplate, TEST_URI);
        BusyFlightsResponse actualResponse = toughJetFlightSupplier.getFlights("","", LocalDate.of(2020,11,11), LocalDate.of(2020,11,11),4);

        assertEquals(response.getCarrier(), actualResponse.getAirline());
        assertEquals(response.getArrivalAirportName(), actualResponse.getDestinationAirportCode());
        assertEquals(response.getDepartureAirportName(), actualResponse.getDepartureAirportCode());
        assertEquals(response.getBasePrice() + response.getTax() - response.getDiscount(), actualResponse.getPrice(), 0);
        assertEquals(response.getOutboundDateTime(), actualResponse.getDepartDateTime().toString());
        assertEquals(response.getInboundDateTime(), actualResponse.getArrivalDateTime().toString());
    }

    @Test
    public void shouldThrowException() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(any(RequestEntity.class), any(Class.class))).thenReturn(ResponseEntity.badRequest().build());

        ToughJetFlightSupplier toughJetFlightSupplier = new ToughJetFlightSupplier(restTemplate, TEST_URI);
        try {
            toughJetFlightSupplier.getFlights("","", LocalDate.of(2020,11,11), LocalDate.of(2020,11,11),4);
        } catch (RuntimeException e) {
            assertEquals("Failed to post request to ToughJet API", e.getMessage());
        }
    }
}
