package com.project.mydraw.repository;

import com.project.mydraw.domain.BackgroundColor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BackgroundColor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BackgroundColorRepository extends JpaRepository<BackgroundColor, Long>, JpaSpecificationExecutor<BackgroundColor> {
}
