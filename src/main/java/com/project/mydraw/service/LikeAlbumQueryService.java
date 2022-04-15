package com.project.mydraw.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.project.mydraw.domain.LikeAlbum;
import com.project.mydraw.domain.*; // for static metamodels
import com.project.mydraw.repository.LikeAlbumRepository;
import com.project.mydraw.service.dto.LikeAlbumCriteria;
import com.project.mydraw.service.dto.LikeAlbumDTO;
import com.project.mydraw.service.mapper.LikeAlbumMapper;

/**
 * Service for executing complex queries for {@link LikeAlbum} entities in the database.
 * The main input is a {@link LikeAlbumCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LikeAlbumDTO} or a {@link Page} of {@link LikeAlbumDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LikeAlbumQueryService extends QueryService<LikeAlbum> {

    private final Logger log = LoggerFactory.getLogger(LikeAlbumQueryService.class);

    private final LikeAlbumRepository likeAlbumRepository;

    private final LikeAlbumMapper likeAlbumMapper;

    public LikeAlbumQueryService(LikeAlbumRepository likeAlbumRepository, LikeAlbumMapper likeAlbumMapper) {
        this.likeAlbumRepository = likeAlbumRepository;
        this.likeAlbumMapper = likeAlbumMapper;
    }

    /**
     * Return a {@link List} of {@link LikeAlbumDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LikeAlbumDTO> findByCriteria(LikeAlbumCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LikeAlbum> specification = createSpecification(criteria);
        return likeAlbumMapper.toDto(likeAlbumRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LikeAlbumDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LikeAlbumDTO> findByCriteria(LikeAlbumCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LikeAlbum> specification = createSpecification(criteria);
        return likeAlbumRepository.findAll(specification, page)
            .map(likeAlbumMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LikeAlbumCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LikeAlbum> specification = createSpecification(criteria);
        return likeAlbumRepository.count(specification);
    }

    /**
     * Function to convert {@link LikeAlbumCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LikeAlbum> createSpecification(LikeAlbumCriteria criteria) {
        Specification<LikeAlbum> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LikeAlbum_.id));
            }
            if (criteria.getExtendedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getExtendedUserId(),
                    root -> root.join(LikeAlbum_.extendedUser, JoinType.LEFT).get(ExtendedUser_.id)));
            }
            if (criteria.getAlbumId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlbumId(),
                    root -> root.join(LikeAlbum_.album, JoinType.LEFT).get(Album_.id)));
            }
        }
        return specification;
    }
}
