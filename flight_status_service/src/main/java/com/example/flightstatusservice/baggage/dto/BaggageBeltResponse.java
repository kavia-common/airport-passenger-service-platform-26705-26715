package com.example.flightstatusservice.baggage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(name = "BaggageBeltResponse", description = "Baggage belt response payload")
public record BaggageBeltResponse(
        UUID id,
        String beltCode,
        String terminal,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {}
