package com.healsync.appointment.exception;

public class PrescriptionNotFoundException extends RuntimeException {
    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}