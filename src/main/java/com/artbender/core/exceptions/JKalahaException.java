package com.artbender.core.exceptions;

/**
 * Base of application exception hierarchy
 *
 * @author Artsiom Leuchanka
 */
public class JKalahaException extends RuntimeException {

    public JKalahaException(String message) {
        super(message);
    }
}
