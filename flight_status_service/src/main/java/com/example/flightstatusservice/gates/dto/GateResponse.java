package com.example.flightstatusservice.gates.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(name = "GateResponse", description = "Gate response payload")
public record GateResponse(
        UUID id,
        String gateCode,
        String terminal,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {}
