package com.project.mydraw.web.rest;

import com.project.mydraw.service.ExtendedUserService;
import com.project.mydraw.web.rest.errors.BadRequestAlertException;
import com.project.mydraw.service.dto.ExtendedUserDTO;
import com.project.mydraw.service.dto.ExtendedUserCriteria;
import com.project.mydraw.service.ExtendedUserQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.project.mydraw.domain.ExtendedUser}.
 */
@RestController
@RequestMapping("/api")
public class ExtendedUserResource {

    private final Logger log = LoggerFactory.getLogger(ExtendedUserResource.class);

    private static final String ENTITY_NAME = "extendedUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtendedUserService extendedUserService;

    private final ExtendedUserQueryService extendedUserQueryService;

    public ExtendedUserResource(ExtendedUserService extendedUserService, ExtendedUserQueryService extendedUserQueryService) {
        this.extendedUserService = extendedUserService;
        this.extendedUserQueryService = extendedUserQueryService;
    }

    /**
     * {@code POST  /extended-users} : Create a new extendedUser.
     *
     * @param extendedUserDTO the extendedUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extendedUserDTO, or with status {@code 400 (Bad Request)} if the extendedUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/extended-users")
    public ResponseEntity<ExtendedUserDTO> createExtendedUser(@Valid @RequestBody ExtendedUserDTO extendedUserDTO) throws URISyntaxException {
        log.debug("REST request to save ExtendedUser : {}", extendedUserDTO);
        if (extendedUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new extendedUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(extendedUserDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        ExtendedUserDTO result = extendedUserService.save(extendedUserDTO);
        return ResponseEntity.created(new URI("/api/extended-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /extended-users} : Updates an existing extendedUser.
     *
     * @param extendedUserDTO the extendedUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extendedUserDTO,
     * or with status {@code 400 (Bad Request)} if the extendedUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extendedUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/extended-users")
    public ResponseEntity<ExtendedUserDTO> updateExtendedUser(@Valid @RequestBody ExtendedUserDTO extendedUserDTO) throws URISyntaxException {
        log.debug("REST request to update ExtendedUser : {}", extendedUserDTO);
        if (extendedUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExtendedUserDTO result = extendedUserService.save(extendedUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, extendedUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /extended-users} : get all the extendedUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extendedUsers in body.
     */
    @GetMapping("/extended-users")
    public ResponseEntity<List<ExtendedUserDTO>> getAllExtendedUsers(ExtendedUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ExtendedUsers by criteria: {}", criteria);
        Page<ExtendedUserDTO> page = extendedUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /extended-users/count} : count all the extendedUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/extended-users/count")
    public ResponseEntity<Long> countExtendedUsers(ExtendedUserCriteria criteria) {
        log.debug("REST request to count ExtendedUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(extendedUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /extended-users/:id} : get the "id" extendedUser.
     *
     * @param id the id of the extendedUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extendedUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/extended-users/{id}")
    public ResponseEntity<ExtendedUserDTO> getExtendedUser(@PathVariable Long id) {
        log.debug("REST request to get ExtendedUser : {}", id);
        Optional<ExtendedUserDTO> extendedUserDTO = extendedUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extendedUserDTO);
    }

    /**
     * {@code DELETE  /extended-users/:id} : delete the "id" extendedUser.
     *
     * @param id the id of the extendedUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/extended-users/{id}")
    public ResponseEntity<Void> deleteExtendedUser(@PathVariable Long id) {
        log.debug("REST request to delete ExtendedUser : {}", id);
        extendedUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
