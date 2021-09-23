package com.travix.medusa.busyflights.domain.crazyair;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CrazyAirFlightSupplier implements FlightSupplier {

    private final RestTemplate restTemplate;
    private final URI hostname;

    public CrazyAirFlightSupplier(RestTemplate restTemplate, @Value("${crazyAirFlightHost}") String hostname) {
        this.restTemplate = restTemplate;
        this.hostname = URI.create(hostname);
    }

    @Override
    public BusyFlightsResponse getFlights(String origin, String destination, LocalDate departureDate, LocalDate returnDate, int passengers) {
        // Would be nicer to change the Request object to have all of these in the constructor,
        // or make the setters chain-able
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
        crazyAirRequest.setOrigin(origin);
        crazyAirRequest.setDestination(destination);
        crazyAirRequest.setDepartureDate(departureDate.toString());
        crazyAirRequest.setReturnDate(returnDate.toString());
        crazyAirRequest.setPassengerCount(passengers);

        RequestEntity<CrazyAirRequest> requestEntity = RequestEntity.post(hostname).body(crazyAirRequest);

        ResponseEntity<CrazyAirResponse> response;
        try {
            response = restTemplate.exchange(requestEntity, CrazyAirResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to post request to CrazyAir API");
        }

        return convertResponse(response.getBody());
    }

    private static BusyFlightsResponse convertResponse(CrazyAirResponse inputResponse) {
        LocalDateTime departureDate = LocalDateTime.parse(inputResponse.getDepartureDate());
        LocalDateTime arrivalDate = LocalDateTime.parse(inputResponse.getArrivalDate());

        return new BusyFlightsResponse()
                .setDepartureAirportCode(inputResponse.getDepartureAirportCode())
                .setDestinationAirportCode(inputResponse.getDestinationAirportCode())
                .setDepartDateTime(departureDate)
                .setArrivalDateTime(arrivalDate)
                .setAirline(inputResponse.getAirline())
                .setSupplier(inputResponse.getAirline())
                .setPrice(inputResponse.getPrice());
    }
}
