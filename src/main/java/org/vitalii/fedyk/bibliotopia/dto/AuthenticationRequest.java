package org.vitalii.fedyk.bibliotopia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.vitalii.fedyk.bibliotopia.annotation.Password;

import static org.vitalii.fedyk.bibliotopia.constant.RegExpConstants.EMAIL;
import static org.vitalii.fedyk.bibliotopia.constant.ValidationMessageConstants.EMAIL_VIOLATION;
import static org.vitalii.fedyk.bibliotopia.constant.ValidationMessageConstants.NOT_NULL_VIOLATION;

public record AuthenticationRequest(@Email(regexp = EMAIL, message = EMAIL_VIOLATION) @NotNull(message = NOT_NULL_VIOLATION) String email,
                                    @Password String password) {
}