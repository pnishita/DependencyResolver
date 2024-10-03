package com.java.dependencyresolver.exception;

public class NotificationParsingException extends RuntimeException{
    public NotificationParsingException(String message) {
        super(message);
    }

    public NotificationParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}

