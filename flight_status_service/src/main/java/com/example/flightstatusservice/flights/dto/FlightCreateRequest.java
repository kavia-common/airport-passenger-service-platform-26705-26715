package com.example.flightstatusservice.flights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;

@Schema(name = "FlightCreateRequest", description = "Create flight request payload")
public record FlightCreateRequest(
        @Schema(description = "Airline IATA/ICAO code", example = "AI")
        @NotBlank @Size(max = 10) String airlineCode,
        @Schema(description = "Flight number", example = "101")
        @NotBlank @Size(max = 10) String flightNumber,
        @Schema(description = "Flight date (local to airport)", example = "2026-02-09")
        @NotNull LocalDate flightDate,
        @Schema(description = "Origin airport code", example = "DEL")
        @Size(max = 10) String originAirport,
        @Schema(description = "Destination airport code", example = "COK")
        @Size(max = 10) String destinationAirport,
        @Schema(description = "Scheduled departure (UTC instant)")
        Instant scheduledDeparture,
        @Schema(description = "Scheduled arrival (UTC instant)")
        Instant scheduledArrival) {}
