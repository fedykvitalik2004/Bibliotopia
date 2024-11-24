package org.vitalii.fedyk.bibliotopia.service;

import org.vitalii.fedyk.bibliotopia.dto.ReadGroupDto;
import org.vitalii.fedyk.bibliotopia.dto.UpdateGroupDto;

import java.util.List;

public interface GroupService {
    ReadGroupDto createGroup(long userId, UpdateGroupDto updateGroupDto);

    List<ReadGroupDto> findAllGroupsAvailableForUser(long userId);

    ReadGroupDto updateGroup(long id, UpdateGroupDto updateGroupDto);

    void deleteGroup(long id);
}
