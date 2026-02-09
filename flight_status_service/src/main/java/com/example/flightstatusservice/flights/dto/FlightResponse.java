package com.example.flightstatusservice.flights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "FlightResponse", description = "Flight response payload")
public record FlightResponse(
        UUID id,
        String airlineCode,
        String flightNumber,
        LocalDate flightDate,
        String originAirport,
        String destinationAirport,
        Instant scheduledDeparture,
        Instant scheduledArrival,
        Instant createdAt,
        Instant updatedAt) {}
