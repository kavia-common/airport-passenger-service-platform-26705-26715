package com.example.flightstatusservice.flights;

import com.example.flightstatusservice.api.ConflictException;
import com.example.flightstatusservice.api.NotFoundException;
import com.example.flightstatusservice.flights.dto.FlightCreateRequest;
import com.example.flightstatusservice.flights.dto.FlightResponse;
import com.example.flightstatusservice.flights.dto.FlightUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
@Tag(name = "Flights", description = "CRUD and query endpoints for flights")
public class FlightController {

    private final FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // PUBLIC_INTERFACE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create flight", description = "Creates a flight. Enforces uniqueness (airline_code, flight_number, flight_date).")
    public FlightResponse create(@Valid @RequestBody FlightCreateRequest request) {
        flightRepository
                .findByAirlineCodeAndFlightNumberAndFlightDate(
                        request.airlineCode(), request.flightNumber(), request.flightDate())
                .ifPresent(existing -> {
                    throw new ConflictException("Flight already exists for airlineCode+flightNumber+flightDate");
                });

        FlightEntity e = new FlightEntity();
        e.setId(UUID.randomUUID());
        e.setAirlineCode(request.airlineCode());
        e.setFlightNumber(request.flightNumber());
        e.setFlightDate(request.flightDate());
        e.setOriginAirport(request.originAirport());
        e.setDestinationAirport(request.destinationAirport());
        e.setScheduledDeparture(request.scheduledDeparture());
        e.setScheduledArrival(request.scheduledArrival());
        // created_at/updated_at are DB-managed defaults; keep null for insert

        FlightEntity saved = flightRepository.save(e);
        return toResponse(saved);
    }

    // PUBLIC_INTERFACE
    @GetMapping("/{id}")
    @Operation(summary = "Get flight by id", description = "Returns a flight by its UUID.")
    public FlightResponse getById(@PathVariable UUID id) {
        FlightEntity e = flightRepository.findById(id).orElseThrow(() -> new NotFoundException("Flight not found"));
        return toResponse(e);
    }

    // PUBLIC_INTERFACE
    @GetMapping
    @Operation(
            summary = "List/search flights",
            description = "Lists flights. Optional filters: flightDate (required for airlineCode filter), airlineCode.")
    public List<FlightResponse> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate,
            @RequestParam(required = false) String airlineCode) {

        List<FlightEntity> items;
        if (flightDate != null && airlineCode != null && !airlineCode.isBlank()) {
            items = flightRepository.findByAirlineCodeAndFlightDate(airlineCode, flightDate);
        } else if (flightDate != null) {
            items = flightRepository.findByFlightDate(flightDate);
        } else {
            items = flightRepository.findAll();
        }
        return items.stream().map(FlightController::toResponse).toList();
    }

    // PUBLIC_INTERFACE
    @PutMapping("/{id}")
    @Operation(summary = "Update flight", description = "Full update of a flight payload.")
    public FlightResponse update(@PathVariable UUID id, @Valid @RequestBody FlightUpdateRequest request) {
        FlightEntity e = flightRepository.findById(id).orElseThrow(() -> new NotFoundException("Flight not found"));

        // Uniqueness check if changing natural key values
        flightRepository
                .findByAirlineCodeAndFlightNumberAndFlightDate(
                        request.airlineCode(), request.flightNumber(), request.flightDate())
                .ifPresent(other -> {
                    if (!other.getId().equals(id)) {
                        throw new ConflictException("Another flight exists with the same airlineCode+flightNumber+flightDate");
                    }
                });

        e.setAirlineCode(request.airlineCode());
        e.setFlightNumber(request.flightNumber());
        e.setFlightDate(request.flightDate());
        e.setOriginAirport(request.originAirport());
        e.setDestinationAirport(request.destinationAirport());
        e.setScheduledDeparture(request.scheduledDeparture());
        e.setScheduledArrival(request.scheduledArrival());

        FlightEntity saved = flightRepository.save(e);
        return toResponse(saved);
    }

    // PUBLIC_INTERFACE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete flight", description = "Deletes a flight by UUID.")
    public void delete(@PathVariable UUID id) {
        if (!flightRepository.existsById(id)) {
            throw new NotFoundException("Flight not found");
        }
        flightRepository.deleteById(id);
    }

    private static FlightResponse toResponse(FlightEntity e) {
        // For newly inserted rows, DB defaults populate createdAt/updatedAt after flush; JPA should read them if returned.
        Instant createdAt = e.getCreatedAt();
        Instant updatedAt = e.getUpdatedAt();
        return new FlightResponse(
                e.getId(),
                e.getAirlineCode(),
                e.getFlightNumber(),
                e.getFlightDate(),
                e.getOriginAirport(),
                e.getDestinationAirport(),
                e.getScheduledDeparture(),
                e.getScheduledArrival(),
                createdAt,
                updatedAt);
    }
}
