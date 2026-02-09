package com.example.flightstatusservice.baggage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "BaggageBeltCreateRequest", description = "Create baggage belt request payload")
public record BaggageBeltCreateRequest(
        @Schema(description = "Baggage belt code", example = "B5")
        @NotBlank @Size(max = 20) String beltCode,
        @Schema(description = "Terminal", example = "T1")
        @Size(max = 20) String terminal,
        @Schema(description = "Whether the belt is active", example = "true")
        boolean active) {}
