package com.example.flightstatusservice.flights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;

@Schema(name = "FlightUpdateRequest", description = "Update flight request payload")
public record FlightUpdateRequest(
        @NotBlank @Size(max = 10) String airlineCode,
        @NotBlank @Size(max = 10) String flightNumber,
        @NotNull LocalDate flightDate,
        @Size(max = 10) String originAirport,
        @Size(max = 10) String destinationAirport,
        Instant scheduledDeparture,
        Instant scheduledArrival) {}
