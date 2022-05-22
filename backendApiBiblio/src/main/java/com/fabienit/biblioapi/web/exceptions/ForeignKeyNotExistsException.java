package com.fabienit.biblioapi.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ForeignKeyNotExistsException
 * 
 * This exception is thrown if the app try to add an entity with no existing
 * foreign keys in the database, return HTTP status 400 BAD REQUEST
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ForeignKeyNotExistsException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 3795028919103544L;

    /**
     * Display message when exception is thrown
     * 
     * @param message
     */
    public ForeignKeyNotExistsException(String message) {
        super(message);
    }

}