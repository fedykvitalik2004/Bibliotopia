package org.vitalii.fedyk.bibliotopia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Group{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<GroupMembership> groupMembershipList;
    private Boolean visible;

    public void addMembership(final GroupMembership groupMembership) {
        groupMembership.setGroup(this);
        if (Objects.isNull(groupMembershipList)) {
            groupMembershipList = new ArrayList<>();
        }
        groupMembershipList.add(groupMembership);
    }
}