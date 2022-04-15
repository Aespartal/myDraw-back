package com.project.mydraw.service.impl;

import com.project.mydraw.service.LikeCommentaryService;
import com.project.mydraw.domain.LikeCommentary;
import com.project.mydraw.repository.LikeCommentaryRepository;
import com.project.mydraw.service.dto.LikeCommentaryDTO;
import com.project.mydraw.service.mapper.LikeCommentaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LikeCommentary}.
 */
@Service
@Transactional
public class LikeCommentaryServiceImpl implements LikeCommentaryService {

    private final Logger log = LoggerFactory.getLogger(LikeCommentaryServiceImpl.class);

    private final LikeCommentaryRepository likeCommentaryRepository;

    private final LikeCommentaryMapper likeCommentaryMapper;

    public LikeCommentaryServiceImpl(LikeCommentaryRepository likeCommentaryRepository, LikeCommentaryMapper likeCommentaryMapper) {
        this.likeCommentaryRepository = likeCommentaryRepository;
        this.likeCommentaryMapper = likeCommentaryMapper;
    }

    @Override
    public LikeCommentaryDTO save(LikeCommentaryDTO likeCommentaryDTO) {
        log.debug("Request to save LikeCommentary : {}", likeCommentaryDTO);
        LikeCommentary likeCommentary = likeCommentaryMapper.toEntity(likeCommentaryDTO);
        likeCommentary = likeCommentaryRepository.save(likeCommentary);
        return likeCommentaryMapper.toDto(likeCommentary);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LikeCommentaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LikeCommentaries");
        return likeCommentaryRepository.findAll(pageable)
            .map(likeCommentaryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LikeCommentaryDTO> findOne(Long id) {
        log.debug("Request to get LikeCommentary : {}", id);
        return likeCommentaryRepository.findById(id)
            .map(likeCommentaryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LikeCommentary : {}", id);
        likeCommentaryRepository.deleteById(id);
    }
}
