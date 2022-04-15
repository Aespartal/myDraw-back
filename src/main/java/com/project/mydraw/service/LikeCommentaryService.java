package com.project.mydraw.service;

import com.project.mydraw.service.dto.LikeCommentaryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.project.mydraw.domain.LikeCommentary}.
 */
public interface LikeCommentaryService {

    /**
     * Save a likeCommentary.
     *
     * @param likeCommentaryDTO the entity to save.
     * @return the persisted entity.
     */
    LikeCommentaryDTO save(LikeCommentaryDTO likeCommentaryDTO);

    /**
     * Get all the likeCommentaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LikeCommentaryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" likeCommentary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeCommentaryDTO> findOne(Long id);

    /**
     * Delete the "id" likeCommentary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
