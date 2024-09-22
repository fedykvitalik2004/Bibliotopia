package org.vitalii.fedyk.bibliotopia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.vitalii.fedyk.bibliotopia.entity.User;
import org.vitalii.fedyk.bibliotopia.dto.RegisterRequest;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegisterRequestMapper {
    @Mappings(value = {
            @Mapping(target = "fullName.firstName", source = "firstName"),
            @Mapping(target = "fullName.lastName", source = "lastName")
    })
    User registerRequestToUser(RegisterRequest registerRequest);
}