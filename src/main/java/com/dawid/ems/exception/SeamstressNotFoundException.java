package com.dawid.ems.exception;

public class SeamstressNotFoundException extends RuntimeException {
    public SeamstressNotFoundException() {
        super();
    }

    public SeamstressNotFoundException(String message) {
        super(message);
    }

    public SeamstressNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeamstressNotFoundException(Throwable cause) {
        super(cause);
    }
}
