package org.vitalii.fedyk.bibliotopia.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.bibliotopia.dto.ReadGroupDto;
import org.vitalii.fedyk.bibliotopia.dto.UpdateGroupDto;
import org.vitalii.fedyk.bibliotopia.entity.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    ReadGroupDto groupToGroupDto(Group group);
    Group updateGroupDtoToGroup(UpdateGroupDto createGroupDto);
}
