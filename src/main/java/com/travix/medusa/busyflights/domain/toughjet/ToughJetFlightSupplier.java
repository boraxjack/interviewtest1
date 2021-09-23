package com.travix.medusa.busyflights.domain.toughjet;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplier;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ToughJetFlightSupplier implements FlightSupplier {

    private final RestTemplate restTemplate;
    private final URI hostname;

    public ToughJetFlightSupplier(RestTemplate restTemplate, @Value("${toughJetFlightHost}") String hostname) {
        this.restTemplate = restTemplate;
        this.hostname = URI.create(hostname);
    }

    @Override
    public BusyFlightsResponse getFlights(String origin, String destination, LocalDate departureDate, LocalDate returnDate, int passengers) {
        ToughJetRequest toughJetRequest = new ToughJetRequest();
        toughJetRequest.setFrom(origin);
        toughJetRequest.setTo(destination);
        toughJetRequest.setOutboundDate(departureDate.toString());
        toughJetRequest.setInboundDate(returnDate.toString());
        toughJetRequest.setNumberOfAdults(passengers);

        RequestEntity<ToughJetRequest> requestEntity = RequestEntity.post(hostname).body(toughJetRequest);

        ResponseEntity<ToughJetResponse> response;
        try {
            response = restTemplate.exchange(requestEntity, ToughJetResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to post request to CrazyAir API");
        }

        return convertResponse(response.getBody());
    }

    private static BusyFlightsResponse convertResponse(ToughJetResponse inputResponse) {
        LocalDateTime departureDate = LocalDateTime.parse(inputResponse.getOutboundDateTime());
        LocalDateTime arrivalDate = LocalDateTime.parse(inputResponse.getInboundDateTime());

        return new BusyFlightsResponse()
                .setDepartureAirportCode(inputResponse.getDepartureAirportName())
                .setDestinationAirportCode(inputResponse.getArrivalAirportName())
                .setDepartDateTime(departureDate)
                .setArrivalDateTime(arrivalDate)
                .setAirline(inputResponse.getCarrier())
                .setSupplier(inputResponse.getCarrier())
                .setPrice(inputResponse.getBasePrice() + inputResponse.getTax() - inputResponse.getDiscount());
        // Unclear whether discount is a percentage or absolute, going with absolute value
    }
}
