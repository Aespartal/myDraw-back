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

import com.project.mydraw.domain.BackgroundColor;
import com.project.mydraw.domain.*; // for static metamodels
import com.project.mydraw.repository.BackgroundColorRepository;
import com.project.mydraw.service.dto.BackgroundColorCriteria;
import com.project.mydraw.service.dto.BackgroundColorDTO;
import com.project.mydraw.service.mapper.BackgroundColorMapper;

/**
 * Service for executing complex queries for {@link BackgroundColor} entities in the database.
 * The main input is a {@link BackgroundColorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BackgroundColorDTO} or a {@link Page} of {@link BackgroundColorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BackgroundColorQueryService extends QueryService<BackgroundColor> {

    private final Logger log = LoggerFactory.getLogger(BackgroundColorQueryService.class);

    private final BackgroundColorRepository backgroundColorRepository;

    private final BackgroundColorMapper backgroundColorMapper;

    public BackgroundColorQueryService(BackgroundColorRepository backgroundColorRepository, BackgroundColorMapper backgroundColorMapper) {
        this.backgroundColorRepository = backgroundColorRepository;
        this.backgroundColorMapper = backgroundColorMapper;
    }

    /**
     * Return a {@link List} of {@link BackgroundColorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BackgroundColorDTO> findByCriteria(BackgroundColorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BackgroundColor> specification = createSpecification(criteria);
        return backgroundColorMapper.toDto(backgroundColorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BackgroundColorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BackgroundColorDTO> findByCriteria(BackgroundColorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BackgroundColor> specification = createSpecification(criteria);
        return backgroundColorRepository.findAll(specification, page)
            .map(backgroundColorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BackgroundColorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BackgroundColor> specification = createSpecification(criteria);
        return backgroundColorRepository.count(specification);
    }

    /**
     * Function to convert {@link BackgroundColorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BackgroundColor> createSpecification(BackgroundColorCriteria criteria) {
        Specification<BackgroundColor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BackgroundColor_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BackgroundColor_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), BackgroundColor_.code));
            }
            if (criteria.getExtendedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getExtendedUserId(),
                    root -> root.join(BackgroundColor_.extendedUsers, JoinType.LEFT).get(ExtendedUser_.id)));
            }
        }
        return specification;
    }
}
