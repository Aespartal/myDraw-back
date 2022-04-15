package com.project.mydraw.repository;

import com.project.mydraw.domain.Commentary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Commentary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long>, JpaSpecificationExecutor<Commentary> {
}
