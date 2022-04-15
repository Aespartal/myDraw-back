package com.project.mydraw.service;

import com.project.mydraw.service.dto.BackgroundColorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.project.mydraw.domain.BackgroundColor}.
 */
public interface BackgroundColorService {

    /**
     * Save a backgroundColor.
     *
     * @param backgroundColorDTO the entity to save.
     * @return the persisted entity.
     */
    BackgroundColorDTO save(BackgroundColorDTO backgroundColorDTO);

    /**
     * Get all the backgroundColors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BackgroundColorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" backgroundColor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BackgroundColorDTO> findOne(Long id);

    /**
     * Delete the "id" backgroundColor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
