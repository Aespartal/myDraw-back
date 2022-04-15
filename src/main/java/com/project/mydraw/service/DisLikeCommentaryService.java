package com.project.mydraw.service;

import com.project.mydraw.service.dto.DisLikeCommentaryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.project.mydraw.domain.DisLikeCommentary}.
 */
public interface DisLikeCommentaryService {

    /**
     * Save a disLikeCommentary.
     *
     * @param disLikeCommentaryDTO the entity to save.
     * @return the persisted entity.
     */
    DisLikeCommentaryDTO save(DisLikeCommentaryDTO disLikeCommentaryDTO);

    /**
     * Get all the disLikeCommentaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisLikeCommentaryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" disLikeCommentary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisLikeCommentaryDTO> findOne(Long id);

    /**
     * Delete the "id" disLikeCommentary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
