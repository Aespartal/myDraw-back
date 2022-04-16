package com.project.mydraw.service.impl;

import com.project.mydraw.domain.BackgroundColor;
import com.project.mydraw.domain.User;
import com.project.mydraw.service.BackgroundColorService;
import com.project.mydraw.service.ExtendedUserService;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.repository.ExtendedUserRepository;
import com.project.mydraw.service.UserService;
import com.project.mydraw.service.dto.BackgroundColorDTO;
import com.project.mydraw.service.dto.ExtendedUserDTO;
import com.project.mydraw.service.dto.UserDTO;
import com.project.mydraw.service.dto.UserMinimalDTO;
import com.project.mydraw.service.mapper.ExtendedUserMapper;
import com.project.mydraw.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExtendedUser}.
 */
@Service
@Transactional
public class ExtendedUserServiceImpl implements ExtendedUserService {

    private final Logger log = LoggerFactory.getLogger(ExtendedUserServiceImpl.class);

    private final ExtendedUserRepository extendedUserRepository;

    private final ExtendedUserMapper extendedUserMapper;

    private final UserMapper userMapper;

    private final UserService userService;

    private final BackgroundColorService backgroundColorService;

    public ExtendedUserServiceImpl(ExtendedUserRepository extendedUserRepository, ExtendedUserMapper extendedUserMapper, UserMapper userMapper, UserService userService, BackgroundColorService backgroundColorService) {
        this.extendedUserRepository = extendedUserRepository;
        this.extendedUserMapper = extendedUserMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.backgroundColorService = backgroundColorService;
    }

    @Override
    public ExtendedUserDTO save(ExtendedUserDTO extendedUserDTO) {
        log.debug("Request to save ExtendedUser : {}", extendedUserDTO);
        ExtendedUser extendedUser = extendedUserMapper.toEntity(extendedUserDTO);
        UserMinimalDTO userMinimalDTO = extendedUserDTO.getUser();
        User user = mapUserMinimalDTOToUser(userMinimalDTO);
        UserDTO userDTO = mapUserToUserDTO(user);
        if(userDTO.getId() == null) {
            user = userService.createUser(userDTO);
        } else {
             user = userService.updateUserWithUserDTO(userDTO);
        }
        fillExtendedUser(extendedUser, user);
        extendedUser = extendedUserRepository.save(extendedUser);
        return extendedUserMapper.toDto(extendedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExtendedUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExtendedUsers");
        return extendedUserRepository.findAll(pageable)
            .map(extendedUserMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExtendedUserDTO> findOne(Long id) {
        log.debug("Request to get ExtendedUser : {}", id);
        return extendedUserRepository.findById(id)
            .map(extendedUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExtendedUser : {}", id);
        Optional<ExtendedUser> extendedUserOptional = extendedUserRepository.findById(id);
        if (!extendedUserOptional.isPresent()) {
            return;
        }
        ExtendedUser extendedUser = extendedUserOptional.get();
        User user = extendedUser.getUser();
        extendedUserRepository.deleteById(id);
        userService.deleteUser(user.getLogin());
        userService.clearUserCaches(user);
    }

    private void fillExtendedUser(ExtendedUser extendedUser, User user) {
        extendedUser.setUser(user);
        if(extendedUser.getBackgroundColor() == null) {
            BackgroundColor backgroundColor = backgroundColorService.getBackgroundColorDefault();
            extendedUser.setBackgroundColor(backgroundColor);
        }
    }


    private User mapUserMinimalDTOToUser(UserMinimalDTO userMinimalDTO) {
        return userMapper.userMinimalDTOToUser(userMinimalDTO);
    }

    private UserDTO mapUserToUserDTO(User user) {
        return userMapper.userToUserDTO(user);
    }
}
