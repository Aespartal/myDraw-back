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

import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.domain.*; // for static metamodels
import com.project.mydraw.repository.ExtendedUserRepository;
import com.project.mydraw.service.dto.ExtendedUserCriteria;
import com.project.mydraw.service.dto.ExtendedUserDTO;
import com.project.mydraw.service.mapper.ExtendedUserMapper;

/**
 * Service for executing complex queries for {@link ExtendedUser} entities in the database.
 * The main input is a {@link ExtendedUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExtendedUserDTO} or a {@link Page} of {@link ExtendedUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExtendedUserQueryService extends QueryService<ExtendedUser> {

    private final Logger log = LoggerFactory.getLogger(ExtendedUserQueryService.class);

    private final ExtendedUserRepository extendedUserRepository;

    private final ExtendedUserMapper extendedUserMapper;

    public ExtendedUserQueryService(ExtendedUserRepository extendedUserRepository, ExtendedUserMapper extendedUserMapper) {
        this.extendedUserRepository = extendedUserRepository;
        this.extendedUserMapper = extendedUserMapper;
    }

    /**
     * Return a {@link List} of {@link ExtendedUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExtendedUserDTO> findByCriteria(ExtendedUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExtendedUser> specification = createSpecification(criteria);
        return extendedUserMapper.toDto(extendedUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExtendedUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtendedUserDTO> findByCriteria(ExtendedUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExtendedUser> specification = createSpecification(criteria);
        return extendedUserRepository.findAll(specification, page)
            .map(extendedUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExtendedUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExtendedUser> specification = createSpecification(criteria);
        return extendedUserRepository.count(specification);
    }

    /**
     * Function to convert {@link ExtendedUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExtendedUser> createSpecification(ExtendedUserCriteria criteria) {
        Specification<ExtendedUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExtendedUser_.id));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), ExtendedUser_.telephone));
            }
            if (criteria.getBirthdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthdate(), ExtendedUser_.birthdate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ExtendedUser_.description));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), ExtendedUser_.gender));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ExtendedUser_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getAlbumId() != null) {
                specification = specification.and(buildSpecification(criteria.getAlbumId(),
                    root -> root.join(ExtendedUser_.albums, JoinType.LEFT).get(Album_.id)));
            }
            if (criteria.getBackgroundColorId() != null) {
                specification = specification.and(buildSpecification(criteria.getBackgroundColorId(),
                    root -> root.join(ExtendedUser_.backgroundColor, JoinType.LEFT).get(BackgroundColor_.id)));
            }
        }
        return specification;
    }
}
