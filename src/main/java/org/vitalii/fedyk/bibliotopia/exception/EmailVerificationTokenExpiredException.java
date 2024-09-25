package org.vitalii.fedyk.bibliotopia.exception;

/**
 * Exception thrown when an email verification token has expired.
 *
 * <p>This exception indicates that the provided email verification token is no longer valid.
 * It extends {@link IllegalStateException} to signify that the current state of the system is invalid.</p>
 */
public class EmailVerificationTokenExpiredException extends IllegalStateException {
    /**
     * Constructs a new {@code EmailVerificationTokenExpiredException} with the specified detail message.
     *
     * @param s the detail message for this exception
     */
    public EmailVerificationTokenExpiredException(final String s) {
        super(s);
    }
}
