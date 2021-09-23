package com.travix.medusa.busyflights.domain.busyflights;

import java.time.LocalDateTime;

public class BusyFlightsResponse {
    private String airline;
    private String supplier;
    private double price;
    private String departureAirportCode;
    private String destinationAirportCode;
    private LocalDateTime departDateTime;
    private LocalDateTime arrivalDateTime;

    public String getAirline() {
        return airline;
    }

    public BusyFlightsResponse setAirline(String airline) {
        this.airline = airline;
        return this;
    }

    public String getSupplier() {
        return supplier;
    }

    public BusyFlightsResponse setSupplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public BusyFlightsResponse setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public BusyFlightsResponse setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
        return this;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public BusyFlightsResponse setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
        return this;
    }

    public LocalDateTime getDepartDateTime() {
        return departDateTime;
    }

    public BusyFlightsResponse setDepartDateTime(LocalDateTime departDateTime) {
        this.departDateTime = departDateTime;
        return this;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public BusyFlightsResponse setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
        return this;
    }
}
