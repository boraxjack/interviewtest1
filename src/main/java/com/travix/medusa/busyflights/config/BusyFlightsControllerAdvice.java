package com.travix.medusa.busyflights.config;

import com.travix.medusa.busyflights.domain.busyflights.FlightNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusyFlightsControllerAdvice {

    @ExceptionHandler(FlightNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String flightNotFoundException() {
        return "Flight not found!";
    }
}
