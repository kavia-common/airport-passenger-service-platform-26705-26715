package com.example.flightstatusservice.baggage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "BaggageBeltUpdateRequest", description = "Update baggage belt request payload")
public record BaggageBeltUpdateRequest(
        @NotBlank @Size(max = 20) String beltCode,
        @Size(max = 20) String terminal,
        boolean active) {}
