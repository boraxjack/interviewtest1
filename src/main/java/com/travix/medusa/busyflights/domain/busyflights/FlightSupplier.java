package com.travix.medusa.busyflights.domain.busyflights;

import java.time.LocalDate;

public interface FlightSupplier {
    BusyFlightsResponse getFlights(String origin, String destination, LocalDate departureDate, LocalDate returnDate, int passengers);
}
