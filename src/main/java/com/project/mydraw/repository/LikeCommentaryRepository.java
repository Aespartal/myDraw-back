package com.project.mydraw.repository;

import com.project.mydraw.domain.LikeCommentary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LikeCommentary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeCommentaryRepository extends JpaRepository<LikeCommentary, Long>, JpaSpecificationExecutor<LikeCommentary> {
}
