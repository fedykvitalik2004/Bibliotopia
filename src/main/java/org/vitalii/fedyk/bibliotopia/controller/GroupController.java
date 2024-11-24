package org.vitalii.fedyk.bibliotopia.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.vitalii.fedyk.bibliotopia.dto.ReadGroupDto;
import org.vitalii.fedyk.bibliotopia.dto.UpdateGroupDto;
import org.vitalii.fedyk.bibliotopia.entity.User;
import org.vitalii.fedyk.bibliotopia.service.GroupService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/groups")
@AllArgsConstructor
public class GroupController {
    private GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadGroupDto createGroup(@AuthenticationPrincipal final UserDetails userDetails,
                                    @RequestBody @Valid final UpdateGroupDto updateGroupDto) {
        return groupService.createGroup(((User) userDetails).getId(), updateGroupDto);
    }

    @GetMapping
    public List<ReadGroupDto> findAllGroupsAvailableForUser(@AuthenticationPrincipal final UserDetails userDetails) {
        return groupService.findAllGroupsAvailableForUser(((User) userDetails).getId());
    }

    @PostMapping(value = "/{id}")
    public ReadGroupDto updateGroup(@PathVariable final long id,
                                    @RequestBody @Valid final UpdateGroupDto updateGroupDto) {
        return groupService.updateGroup(id, updateGroupDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable final long id) {
        groupService.deleteGroup(id);
    }
}
