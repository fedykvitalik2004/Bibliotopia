package org.vitalii.fedyk.bibliotopia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vitalii.fedyk.bibliotopia.dto.ReadGroupDto;
import org.vitalii.fedyk.bibliotopia.entity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT new org.vitalii.fedyk.bibliotopia.dto.ReadGroupDto(g.id, g.name, g.description, g.visible) " +
           "FROM Group g " +
           "WHERE g.visible = TRUE OR  EXISTS (" +
           "    SELECT 1 " +
           "    FROM GroupMembership gm " +
           "    WHERE gm.group = g AND gm.user.id = :userId" +
           ")")
    List<ReadGroupDto> findAllGroupsVisibleForUser(long userId);
}
