package com.example.flightstatusservice.gates.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "GateCreateRequest", description = "Create gate request payload")
public record GateCreateRequest(
        @Schema(description = "Gate code", example = "A12")
        @NotBlank @Size(max = 20) String gateCode,
        @Schema(description = "Terminal", example = "T1")
        @Size(max = 20) String terminal,
        @Schema(description = "Whether the gate is active", example = "true")
        boolean active) {}
