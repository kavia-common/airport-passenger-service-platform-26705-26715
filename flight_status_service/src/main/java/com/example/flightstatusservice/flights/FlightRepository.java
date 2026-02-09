package com.example.flightstatusservice.flights;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, UUID> {

    Optional<FlightEntity> findByAirlineCodeAndFlightNumberAndFlightDate(
            String airlineCode, String flightNumber, LocalDate flightDate);

    List<FlightEntity> findByFlightDate(LocalDate flightDate);

    List<FlightEntity> findByAirlineCodeAndFlightDate(String airlineCode, LocalDate flightDate);
}
