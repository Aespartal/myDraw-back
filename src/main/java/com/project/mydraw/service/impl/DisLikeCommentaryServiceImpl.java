package com.project.mydraw.service.impl;

import com.project.mydraw.service.DisLikeCommentaryService;
import com.project.mydraw.domain.DisLikeCommentary;
import com.project.mydraw.repository.DisLikeCommentaryRepository;
import com.project.mydraw.service.dto.DisLikeCommentaryDTO;
import com.project.mydraw.service.mapper.DisLikeCommentaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DisLikeCommentary}.
 */
@Service
@Transactional
public class DisLikeCommentaryServiceImpl implements DisLikeCommentaryService {

    private final Logger log = LoggerFactory.getLogger(DisLikeCommentaryServiceImpl.class);

    private final DisLikeCommentaryRepository disLikeCommentaryRepository;

    private final DisLikeCommentaryMapper disLikeCommentaryMapper;

    public DisLikeCommentaryServiceImpl(DisLikeCommentaryRepository disLikeCommentaryRepository, DisLikeCommentaryMapper disLikeCommentaryMapper) {
        this.disLikeCommentaryRepository = disLikeCommentaryRepository;
        this.disLikeCommentaryMapper = disLikeCommentaryMapper;
    }

    @Override
    public DisLikeCommentaryDTO save(DisLikeCommentaryDTO disLikeCommentaryDTO) {
        log.debug("Request to save DisLikeCommentary : {}", disLikeCommentaryDTO);
        DisLikeCommentary disLikeCommentary = disLikeCommentaryMapper.toEntity(disLikeCommentaryDTO);
        disLikeCommentary = disLikeCommentaryRepository.save(disLikeCommentary);
        return disLikeCommentaryMapper.toDto(disLikeCommentary);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisLikeCommentaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DisLikeCommentaries");
        return disLikeCommentaryRepository.findAll(pageable)
            .map(disLikeCommentaryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DisLikeCommentaryDTO> findOne(Long id) {
        log.debug("Request to get DisLikeCommentary : {}", id);
        return disLikeCommentaryRepository.findById(id)
            .map(disLikeCommentaryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DisLikeCommentary : {}", id);
        disLikeCommentaryRepository.deleteById(id);
    }
}
