package com.fabienit.biblioapi.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EntityAlreadyExistsException
 * 
 * This exception is thrown if the app try to add an already existing entity to
 * the database, return HTTP status 400 BAD REQUEST
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EntityAlreadyExistsException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Display message when exception is thrown
     * 
     * @param message
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

}