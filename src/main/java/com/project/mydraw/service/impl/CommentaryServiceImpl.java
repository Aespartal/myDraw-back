package com.project.mydraw.service.impl;

import com.project.mydraw.service.CommentaryService;
import com.project.mydraw.domain.Commentary;
import com.project.mydraw.repository.CommentaryRepository;
import com.project.mydraw.service.dto.CommentaryDTO;
import com.project.mydraw.service.mapper.CommentaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Commentary}.
 */
@Service
@Transactional
public class CommentaryServiceImpl implements CommentaryService {

    private final Logger log = LoggerFactory.getLogger(CommentaryServiceImpl.class);

    private final CommentaryRepository commentaryRepository;

    private final CommentaryMapper commentaryMapper;

    public CommentaryServiceImpl(CommentaryRepository commentaryRepository, CommentaryMapper commentaryMapper) {
        this.commentaryRepository = commentaryRepository;
        this.commentaryMapper = commentaryMapper;
    }

    @Override
    public CommentaryDTO save(CommentaryDTO commentaryDTO) {
        log.debug("Request to save Commentary : {}", commentaryDTO);
        Commentary commentary = commentaryMapper.toEntity(commentaryDTO);
        commentary = commentaryRepository.save(commentary);
        return commentaryMapper.toDto(commentary);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Commentaries");
        return commentaryRepository.findAll(pageable)
            .map(commentaryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CommentaryDTO> findOne(Long id) {
        log.debug("Request to get Commentary : {}", id);
        return commentaryRepository.findById(id)
            .map(commentaryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commentary : {}", id);
        commentaryRepository.deleteById(id);
    }
}
