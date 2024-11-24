package org.vitalii.fedyk.bibliotopia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateGroupDto {
    @NotBlank(message = "exception_name_cannot_be_blank")
    private String name;
    @Size(min = 1)
    private String description;
    private boolean visible;
}
