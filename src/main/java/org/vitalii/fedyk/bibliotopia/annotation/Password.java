package org.vitalii.fedyk.bibliotopia.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

import static org.vitalii.fedyk.bibliotopia.constant.RegExpConstants.*;
import static org.vitalii.fedyk.bibliotopia.constant.ValidationMessageConstants.*;

/**
 * Annotation to validate a password.
 *
 * @author Vitalii Fedyk
 * @since 1.0
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@NotNull(message = NOT_NULL_VIOLATION)
@Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters long.")
@Pattern(regexp = ONE_DIGIT, message = ONE_DIGIT_VIOLATION)
@Pattern(regexp = LOWER_CASE, message = LOWER_CASE_VIOLATION)
@Pattern(regexp = UPPER_CASE, message = UPPER_CASE_VIOLATION)
@Pattern(regexp = SPECIAL_CHAR, message = SPECIAL_CHAR_VIOLATION)
public @interface Password {
    /**
     * The default message for this constraint.
     *
     * @return the default message
     */
    String message() default "Password format is incorrect.";

    /**
     * The default groups for this constraint.
     *
     * @return the default groups
     */
    Class<?>[] groups() default {};

    /**
     * The default payload for this constraint.
     *
     * @return the default payload
     */
    Class<? extends Payload>[] payload() default {};
}
