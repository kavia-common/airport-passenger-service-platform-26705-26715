package com.example.flightstatusservice.api;

/**
 * Thrown when a request violates a uniqueness/business constraint.
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
