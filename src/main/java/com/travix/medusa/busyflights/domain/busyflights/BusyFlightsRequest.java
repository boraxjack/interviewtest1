package com.travix.medusa.busyflights.domain.busyflights;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BusyFlightsRequest {

    @NotBlank(message = "Origin is required")
    private String origin;
    @NotBlank(message = "Destination is required")
    private String destination;
    @NotBlank(message = "Departure date is required")
    private String departureDate;
    @NotBlank(message = "Return date is required")
    private String returnDate;
    @Min(value = 1, message = "Minimum value is 1")
    @Max(value = 4, message = "Maximum value is 4")
    private int numberOfPassengers;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final String returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(final int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
