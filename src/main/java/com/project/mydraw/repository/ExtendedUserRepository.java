package com.project.mydraw.repository;

import com.project.mydraw.domain.ExtendedUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExtendedUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtendedUserRepository extends JpaRepository<ExtendedUser, Long>, JpaSpecificationExecutor<ExtendedUser> {
}
