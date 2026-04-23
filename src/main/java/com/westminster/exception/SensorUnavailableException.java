package com.westminster.exception;

public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String message) {
        super(message);
    }
}