package com.project.mydraw.service;

import com.project.mydraw.service.dto.LikeAlbumDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.project.mydraw.domain.LikeAlbum}.
 */
public interface LikeAlbumService {

    /**
     * Save a likeAlbum.
     *
     * @param likeAlbumDTO the entity to save.
     * @return the persisted entity.
     */
    LikeAlbumDTO save(LikeAlbumDTO likeAlbumDTO);

    /**
     * Get all the likeAlbums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LikeAlbumDTO> findAll(Pageable pageable);


    /**
     * Get the "id" likeAlbum.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeAlbumDTO> findOne(Long id);

    /**
     * Delete the "id" likeAlbum.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
