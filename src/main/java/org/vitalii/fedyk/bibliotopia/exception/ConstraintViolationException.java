package org.vitalii.fedyk.bibliotopia.exception;

public class ConstraintViolationException extends LocalizedException {
    public ConstraintViolationException(final String messageKey) {
        super(messageKey);
    }
}
