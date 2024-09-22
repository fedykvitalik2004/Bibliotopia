package org.vitalii.fedyk.bibliotopia.exception;

/**
 * Exception thrown when the user is already registered
 */
public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(final String message) {
        super(message);
    }
}
