package com.project.mydraw.service.impl;

import com.project.mydraw.service.LikeAlbumService;
import com.project.mydraw.domain.LikeAlbum;
import com.project.mydraw.repository.LikeAlbumRepository;
import com.project.mydraw.service.dto.LikeAlbumDTO;
import com.project.mydraw.service.mapper.LikeAlbumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LikeAlbum}.
 */
@Service
@Transactional
public class LikeAlbumServiceImpl implements LikeAlbumService {

    private final Logger log = LoggerFactory.getLogger(LikeAlbumServiceImpl.class);

    private final LikeAlbumRepository likeAlbumRepository;

    private final LikeAlbumMapper likeAlbumMapper;

    public LikeAlbumServiceImpl(LikeAlbumRepository likeAlbumRepository, LikeAlbumMapper likeAlbumMapper) {
        this.likeAlbumRepository = likeAlbumRepository;
        this.likeAlbumMapper = likeAlbumMapper;
    }

    @Override
    public LikeAlbumDTO save(LikeAlbumDTO likeAlbumDTO) {
        log.debug("Request to save LikeAlbum : {}", likeAlbumDTO);
        LikeAlbum likeAlbum = likeAlbumMapper.toEntity(likeAlbumDTO);
        likeAlbum = likeAlbumRepository.save(likeAlbum);
        return likeAlbumMapper.toDto(likeAlbum);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LikeAlbumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LikeAlbums");
        return likeAlbumRepository.findAll(pageable)
            .map(likeAlbumMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LikeAlbumDTO> findOne(Long id) {
        log.debug("Request to get LikeAlbum : {}", id);
        return likeAlbumRepository.findById(id)
            .map(likeAlbumMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LikeAlbum : {}", id);
        likeAlbumRepository.deleteById(id);
    }
}
