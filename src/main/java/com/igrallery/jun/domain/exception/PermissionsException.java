package com.igrallery.jun.domain.exception;


public class PermissionsException extends RuntimeException{
    public PermissionsException() {
        super();
    }

    public PermissionsException(String message) {
        super(message);
    }

    public PermissionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionsException(Throwable cause) {
        super(cause);
    }

    protected PermissionsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
