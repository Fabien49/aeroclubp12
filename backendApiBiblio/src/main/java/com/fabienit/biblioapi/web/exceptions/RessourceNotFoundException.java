package com.fabienit.biblioapi.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RessourceNotFound
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends RuntimeException {

    /**
     * This exception is thrown if the app cannot find a specific entity in the
     * database, return HTTP status 404 NOT FOUND
     */
    private static final long serialVersionUID = 1L;

    /**
     * Display message when exception is thrown
     * 
     * @param message
     */
    public RessourceNotFoundException(String message) {
        super(message);
    }

}