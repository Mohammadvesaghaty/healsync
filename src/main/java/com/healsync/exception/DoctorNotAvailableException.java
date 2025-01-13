package com.healsync.exception;

public class DoctorNotAvailableException extends RuntimeException {
    public DoctorNotAvailableException(String message) {
        super(message);
    }
}
