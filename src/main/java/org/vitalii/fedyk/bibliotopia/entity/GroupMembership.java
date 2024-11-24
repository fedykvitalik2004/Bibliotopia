package org.vitalii.fedyk.bibliotopia.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.vitalii.fedyk.bibliotopia.entity.converter.GroupRoleConverter;
import org.vitalii.fedyk.bibliotopia.enumeration.GroupRole;

import java.time.ZonedDateTime;

@Entity
@Table(name = "group_memberships")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class GroupMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = GroupRoleConverter.class)
    private GroupRole groupRole;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private ZonedDateTime joined;
}