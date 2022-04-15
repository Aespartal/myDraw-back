package com.project.mydraw.service;

import com.project.mydraw.service.dto.CommentaryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.project.mydraw.domain.Commentary}.
 */
public interface CommentaryService {

    /**
     * Save a commentary.
     *
     * @param commentaryDTO the entity to save.
     * @return the persisted entity.
     */
    CommentaryDTO save(CommentaryDTO commentaryDTO);

    /**
     * Get all the commentaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommentaryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" commentary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentaryDTO> findOne(Long id);

    /**
     * Delete the "id" commentary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
