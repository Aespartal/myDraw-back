package com.project.mydraw.service.impl;

import com.project.mydraw.service.ExtendedUserService;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.repository.ExtendedUserRepository;
import com.project.mydraw.repository.UserRepository;
import com.project.mydraw.service.dto.ExtendedUserDTO;
import com.project.mydraw.service.mapper.ExtendedUserMapper;
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

    private final UserRepository userRepository;

    public ExtendedUserServiceImpl(ExtendedUserRepository extendedUserRepository, ExtendedUserMapper extendedUserMapper, UserRepository userRepository) {
        this.extendedUserRepository = extendedUserRepository;
        this.extendedUserMapper = extendedUserMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ExtendedUserDTO save(ExtendedUserDTO extendedUserDTO) {
        log.debug("Request to save ExtendedUser : {}", extendedUserDTO);
        ExtendedUser extendedUser = extendedUserMapper.toEntity(extendedUserDTO);
        Long userId = extendedUserDTO.getUserId();
        userRepository.findById(userId).ifPresent(extendedUser::user);
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
        extendedUserRepository.deleteById(id);
    }
}
