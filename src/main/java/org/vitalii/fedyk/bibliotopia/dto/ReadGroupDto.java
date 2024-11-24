package org.vitalii.fedyk.bibliotopia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReadGroupDto {
    private Long id;
    private String name;
    private String description;
    private boolean visible;
}
