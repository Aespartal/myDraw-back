package com.project.mydraw.repository;

import com.project.mydraw.domain.DisLikeCommentary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DisLikeCommentary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisLikeCommentaryRepository extends JpaRepository<DisLikeCommentary, Long>, JpaSpecificationExecutor<DisLikeCommentary> {
}
