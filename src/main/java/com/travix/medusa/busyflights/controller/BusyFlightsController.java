package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.FlightNotFoundException;
import com.travix.medusa.busyflights.services.BusyFlightsService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller("/api")
@Validated
public class BusyFlightsController {

    private final BusyFlightsService busyFlightsService;

    public BusyFlightsController(BusyFlightsService busyFlightsService) {
        this.busyFlightsService = busyFlightsService;
    }

    @PostMapping(value = "/flights", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusyFlightsResponse> getFlights(@Valid @RequestBody BusyFlightsRequest request) {
        List<BusyFlightsResponse> flights = busyFlightsService.getFlights(request);
        if (flights.isEmpty()) {
            throw new FlightNotFoundException();
        }
        return flights;
    }

}
