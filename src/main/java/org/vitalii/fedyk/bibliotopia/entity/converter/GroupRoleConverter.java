package org.vitalii.fedyk.bibliotopia.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.vitalii.fedyk.bibliotopia.enumeration.GroupRole;

@Converter
public class GroupRoleConverter implements AttributeConverter<GroupRole, Byte> {
    @Override
    public Byte convertToDatabaseColumn(final GroupRole attribute) {
        return switch (attribute) {
            case ADMIN -> (byte) 1;
            case USER -> (byte) 2;
            case READER -> (byte) 3;
        };
    }

    @Override
    public GroupRole convertToEntityAttribute(Byte dbData) {
        return switch (dbData) {
            case 1 -> GroupRole.ADMIN;
            case 2 -> GroupRole.USER;
            case 3 -> GroupRole.READER;
            default -> throw new IllegalStateException("Unexpected value: " + dbData);
        };
    }
}
