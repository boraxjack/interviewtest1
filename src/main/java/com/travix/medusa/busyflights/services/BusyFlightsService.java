package com.travix.medusa.busyflights.services;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.FlightSupplier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BusyFlightsService {

    private final List<FlightSupplier> flightSuppliers;

    public BusyFlightsService(List<FlightSupplier> flightSuppliers) {
        this.flightSuppliers = flightSuppliers;
    }

    public List<BusyFlightsResponse> getFlights(BusyFlightsRequest busyFlightsRequest) {
        return flightSuppliers.stream()
                .map(fs -> fs.getFlights(busyFlightsRequest.getOrigin(), busyFlightsRequest.getDestination(), LocalDate.parse(busyFlightsRequest.getDepartureDate()), LocalDate.parse(busyFlightsRequest.getReturnDate()), busyFlightsRequest.getNumberOfPassengers()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
