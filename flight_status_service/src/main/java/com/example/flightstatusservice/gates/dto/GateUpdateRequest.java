package com.example.flightstatusservice.gates.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "GateUpdateRequest", description = "Update gate request payload")
public record GateUpdateRequest(
        @NotBlank @Size(max = 20) String gateCode,
        @Size(max = 20) String terminal,
        boolean active) {}
