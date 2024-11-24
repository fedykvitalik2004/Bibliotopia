package org.vitalii.fedyk.bibliotopia.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.bibliotopia.dto.ReadGroupDto;
import org.vitalii.fedyk.bibliotopia.dto.UpdateGroupDto;
import org.vitalii.fedyk.bibliotopia.entity.Group;
import org.vitalii.fedyk.bibliotopia.entity.GroupMembership;
import org.vitalii.fedyk.bibliotopia.enumeration.GroupRole;
import org.vitalii.fedyk.bibliotopia.exception.ConstraintViolationException;
import org.vitalii.fedyk.bibliotopia.mapper.GroupMapper;
import org.vitalii.fedyk.bibliotopia.repository.GroupRepository;
import org.vitalii.fedyk.bibliotopia.repository.UserRepository;
import org.vitalii.fedyk.bibliotopia.service.GroupService;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    private final UserRepository userRepository;
    private GroupRepository groupRepository;
    private GroupMapper groupMapper;

    @Override
    public ReadGroupDto createGroup(final long userId, final UpdateGroupDto updateGroupDto) {
        if (groupRepository.exists(retrieveGroupWithNameForComparison(updateGroupDto.getName()))) {
            throw new ConstraintViolationException("exception_group_with_such_name_already_exists");
        }
        final Group group = groupMapper.updateGroupDtoToGroup(updateGroupDto);
        createAdminGroupMembership(group, userId);
        return groupMapper.groupToGroupDto(groupRepository.save(group));
    }

    private void createAdminGroupMembership(final Group group, final long userId) {
        final GroupMembership groupMembership = GroupMembership.builder()
                .user(userRepository.getReferenceById(userId))
                .groupRole(GroupRole.ADMIN)
                .build();
        group.addMembership(groupMembership);
    }

    private Example<Group> retrieveGroupWithNameForComparison(String name) {
        return Example.of(Group.builder()
                .name(name)
                .build());
    }

    @Override
    @Transactional
    public List<ReadGroupDto> findAllGroupsAvailableForUser(final long userId) {
        return groupRepository.findAllGroupsVisibleForUser(userId);
    }

    @Override
    public ReadGroupDto updateGroup(long groupId, UpdateGroupDto updateGroupDto) {
        if (groupRepository.exists(retrieveGroupWithNameForComparison(updateGroupDto.getName()))) {
            throw new ConstraintViolationException("exception_group_with_such_name_already_exists");
        }
        final Group group = groupRepository.findById(groupId)
                .orElse(null);
        group.setName(updateGroupDto.getName());
        group.setDescription(updateGroupDto.getDescription());
        group.setVisible(updateGroupDto.isVisible());
        return groupMapper.groupToGroupDto(group);
    }

    @Override
    public void deleteGroup(long id) {
        groupRepository.deleteById(id);
    }
}
