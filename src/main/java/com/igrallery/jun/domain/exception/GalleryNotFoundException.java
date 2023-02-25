package com.igrallery.jun.domain.exception;


public class GalleryNotFoundException extends RuntimeException {
    public GalleryNotFoundException() {
        super();
    }

    public GalleryNotFoundException(String message) {
        super(message);
    }

    public GalleryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GalleryNotFoundException(Throwable cause) {
        super(cause);
    }

    protected GalleryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
