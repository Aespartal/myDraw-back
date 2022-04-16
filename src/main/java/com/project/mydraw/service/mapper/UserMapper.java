package com.project.mydraw.service.mapper;

import com.project.mydraw.domain.Authority;
import com.project.mydraw.domain.User;
import com.project.mydraw.service.dto.UserDTO;

import com.project.mydraw.service.dto.UserMinimalDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public List<UserMinimalDTO> usersToUserMinimalDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserMinimalDTO)
            .collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public UserMinimalDTO userToUserMinimalDTO(User user) {
        return new UserMinimalDTO(user);
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public List<User> userMinimalDTOsToUsers(List<UserMinimalDTO> userMinimalDTOs) {
        return userMinimalDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userMinimalDTOToUser)
            .collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }

    public User userMinimalDTOToUser(UserMinimalDTO userMinimalDTO) {
        if (userMinimalDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userMinimalDTO.getId());
            user.setLogin(userMinimalDTO.getLogin());
            user.setFirstName(userMinimalDTO.getFirstName());
            user.setLastName(userMinimalDTO.getLastName());
            user.setEmail(userMinimalDTO.getEmail());
            user.setImageUrl(userMinimalDTO.getImageUrl());
            user.setActivated(userMinimalDTO.isActivated());
            Set<Authority> authorities = this.authoritiesFromStrings(userMinimalDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }


    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
