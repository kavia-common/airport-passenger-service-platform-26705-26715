package com.example.flightstatusservice.api;

/**
 * Thrown when a requested resource is not found.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
