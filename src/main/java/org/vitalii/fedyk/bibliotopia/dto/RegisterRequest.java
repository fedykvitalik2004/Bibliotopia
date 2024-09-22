package org.vitalii.fedyk.bibliotopia.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.vitalii.fedyk.bibliotopia.annotation.Password;
import org.vitalii.fedyk.bibliotopia.utility.json.CapitalizeDeserializer;

import static org.vitalii.fedyk.bibliotopia.constant.RegExpConstants.EMAIL;
import static org.vitalii.fedyk.bibliotopia.constant.RegExpConstants.ONLY_LETTERS;
import static org.vitalii.fedyk.bibliotopia.constant.ValidationMessageConstants.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RegisterRequest {
    @NotNull(message = NOT_NULL_VIOLATION)
    @Pattern(regexp = ONLY_LETTERS, message = ONLY_LETTERS_VIOLATION)
    @JsonDeserialize(using = CapitalizeDeserializer.class)
    private String firstName;
    @NotNull(message = NOT_NULL_VIOLATION)
    @Pattern(regexp = ONLY_LETTERS, message = ONLY_LETTERS_VIOLATION)
    @JsonDeserialize(using = CapitalizeDeserializer.class)
    private String lastName;
    @Email(regexp = EMAIL, message = EMAIL_VIOLATION)
    @NotNull(message = NOT_NULL_VIOLATION)
    private String email;
    @Password
    private String password;
}
