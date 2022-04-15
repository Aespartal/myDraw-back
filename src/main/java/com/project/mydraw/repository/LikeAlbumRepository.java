package com.project.mydraw.repository;

import com.project.mydraw.domain.LikeAlbum;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LikeAlbum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeAlbumRepository extends JpaRepository<LikeAlbum, Long>, JpaSpecificationExecutor<LikeAlbum> {
}
