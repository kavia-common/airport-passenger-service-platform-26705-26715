package com.example.flightstatusservice.api;

import java.time.Instant;
import java.util.List;

/**
 * Standard error response for this service.
 */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldViolation> fieldViolations) {

    public record FieldViolation(String field, String message) {}
}
