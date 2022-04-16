package com.project.mydraw.service.impl;

import com.project.mydraw.service.BackgroundColorService;
import com.project.mydraw.domain.BackgroundColor;
import com.project.mydraw.repository.BackgroundColorRepository;
import com.project.mydraw.service.dto.BackgroundColorDTO;
import com.project.mydraw.service.mapper.BackgroundColorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BackgroundColor}.
 */
@Service
@Transactional
public class BackgroundColorServiceImpl implements BackgroundColorService {

    private final Logger log = LoggerFactory.getLogger(BackgroundColorServiceImpl.class);

    private final BackgroundColorRepository backgroundColorRepository;

    private final BackgroundColorMapper backgroundColorMapper;

    private final static long backgroundColor = 1;

    public BackgroundColorServiceImpl(BackgroundColorRepository backgroundColorRepository, BackgroundColorMapper backgroundColorMapper) {
        this.backgroundColorRepository = backgroundColorRepository;
        this.backgroundColorMapper = backgroundColorMapper;
    }

    @Override
    public BackgroundColorDTO save(BackgroundColorDTO backgroundColorDTO) {
        log.debug("Request to save BackgroundColor : {}", backgroundColorDTO);
        BackgroundColor backgroundColor = backgroundColorMapper.toEntity(backgroundColorDTO);
        backgroundColor = backgroundColorRepository.save(backgroundColor);
        return backgroundColorMapper.toDto(backgroundColor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BackgroundColorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BackgroundColors");
        return backgroundColorRepository.findAll(pageable)
            .map(backgroundColorMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BackgroundColorDTO> findOne(Long id) {
        log.debug("Request to get BackgroundColor : {}", id);
        return backgroundColorRepository.findById(id)
            .map(backgroundColorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BackgroundColor : {}", id);
        backgroundColorRepository.deleteById(id);
    }

    @Override
    public BackgroundColor getBackgroundColorDefault() {
        return backgroundColorRepository.findById(backgroundColor).get();
    }
}
