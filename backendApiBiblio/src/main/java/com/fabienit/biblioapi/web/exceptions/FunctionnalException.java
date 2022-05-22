package com.fabienit.biblioapi.web.exceptions;

public class FunctionnalException extends Exception {

    public FunctionnalException() {
        super();
    }

    public FunctionnalException(String message) {
        super(message);
    }

    public FunctionnalException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionnalException(Throwable cause) {
        super(cause);
    }

    protected FunctionnalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
