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

import com.project.mydraw.domain.DisLikeCommentary;
import com.project.mydraw.domain.*; // for static metamodels
import com.project.mydraw.repository.DisLikeCommentaryRepository;
import com.project.mydraw.service.dto.DisLikeCommentaryCriteria;
import com.project.mydraw.service.dto.DisLikeCommentaryDTO;
import com.project.mydraw.service.mapper.DisLikeCommentaryMapper;

/**
 * Service for executing complex queries for {@link DisLikeCommentary} entities in the database.
 * The main input is a {@link DisLikeCommentaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DisLikeCommentaryDTO} or a {@link Page} of {@link DisLikeCommentaryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisLikeCommentaryQueryService extends QueryService<DisLikeCommentary> {

    private final Logger log = LoggerFactory.getLogger(DisLikeCommentaryQueryService.class);

    private final DisLikeCommentaryRepository disLikeCommentaryRepository;

    private final DisLikeCommentaryMapper disLikeCommentaryMapper;

    public DisLikeCommentaryQueryService(DisLikeCommentaryRepository disLikeCommentaryRepository, DisLikeCommentaryMapper disLikeCommentaryMapper) {
        this.disLikeCommentaryRepository = disLikeCommentaryRepository;
        this.disLikeCommentaryMapper = disLikeCommentaryMapper;
    }

    /**
     * Return a {@link List} of {@link DisLikeCommentaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DisLikeCommentaryDTO> findByCriteria(DisLikeCommentaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DisLikeCommentary> specification = createSpecification(criteria);
        return disLikeCommentaryMapper.toDto(disLikeCommentaryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DisLikeCommentaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DisLikeCommentaryDTO> findByCriteria(DisLikeCommentaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DisLikeCommentary> specification = createSpecification(criteria);
        return disLikeCommentaryRepository.findAll(specification, page)
            .map(disLikeCommentaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisLikeCommentaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DisLikeCommentary> specification = createSpecification(criteria);
        return disLikeCommentaryRepository.count(specification);
    }

    /**
     * Function to convert {@link DisLikeCommentaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DisLikeCommentary> createSpecification(DisLikeCommentaryCriteria criteria) {
        Specification<DisLikeCommentary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DisLikeCommentary_.id));
            }
            if (criteria.getExtendedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getExtendedUserId(),
                    root -> root.join(DisLikeCommentary_.extendedUser, JoinType.LEFT).get(ExtendedUser_.id)));
            }
            if (criteria.getComentaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getComentaryId(),
                    root -> root.join(DisLikeCommentary_.comentary, JoinType.LEFT).get(Commentary_.id)));
            }
        }
        return specification;
    }
}
