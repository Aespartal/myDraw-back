package com.project.mydraw.web.rest;

import com.project.mydraw.service.BackgroundColorService;
import com.project.mydraw.web.rest.errors.BadRequestAlertException;
import com.project.mydraw.service.dto.BackgroundColorDTO;
import com.project.mydraw.service.dto.BackgroundColorCriteria;
import com.project.mydraw.service.BackgroundColorQueryService;

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
import java.util.Optional;

/**
 * REST controller for managing {@link com.project.mydraw.domain.BackgroundColor}.
 */
@RestController
@RequestMapping("/api")
public class BackgroundColorResource {

    private final Logger log = LoggerFactory.getLogger(BackgroundColorResource.class);

    private static final String ENTITY_NAME = "backgroundColor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BackgroundColorService backgroundColorService;

    private final BackgroundColorQueryService backgroundColorQueryService;

    public BackgroundColorResource(BackgroundColorService backgroundColorService, BackgroundColorQueryService backgroundColorQueryService) {
        this.backgroundColorService = backgroundColorService;
        this.backgroundColorQueryService = backgroundColorQueryService;
    }

    /**
     * {@code POST  /background-colors} : Create a new backgroundColor.
     *
     * @param backgroundColorDTO the backgroundColorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new backgroundColorDTO, or with status {@code 400 (Bad Request)} if the backgroundColor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/background-colors")
    public ResponseEntity<BackgroundColorDTO> createBackgroundColor(@Valid @RequestBody BackgroundColorDTO backgroundColorDTO) throws URISyntaxException {
        log.debug("REST request to save BackgroundColor : {}", backgroundColorDTO);
        if (backgroundColorDTO.getId() != null) {
            throw new BadRequestAlertException("A new backgroundColor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BackgroundColorDTO result = backgroundColorService.save(backgroundColorDTO);
        return ResponseEntity.created(new URI("/api/background-colors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /background-colors} : Updates an existing backgroundColor.
     *
     * @param backgroundColorDTO the backgroundColorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated backgroundColorDTO,
     * or with status {@code 400 (Bad Request)} if the backgroundColorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the backgroundColorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/background-colors")
    public ResponseEntity<BackgroundColorDTO> updateBackgroundColor(@Valid @RequestBody BackgroundColorDTO backgroundColorDTO) throws URISyntaxException {
        log.debug("REST request to update BackgroundColor : {}", backgroundColorDTO);
        if (backgroundColorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BackgroundColorDTO result = backgroundColorService.save(backgroundColorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, backgroundColorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /background-colors} : get all the backgroundColors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of backgroundColors in body.
     */
    @GetMapping("/background-colors")
    public ResponseEntity<List<BackgroundColorDTO>> getAllBackgroundColors(BackgroundColorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BackgroundColors by criteria: {}", criteria);
        Page<BackgroundColorDTO> page = backgroundColorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /background-colors/count} : count all the backgroundColors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/background-colors/count")
    public ResponseEntity<Long> countBackgroundColors(BackgroundColorCriteria criteria) {
        log.debug("REST request to count BackgroundColors by criteria: {}", criteria);
        return ResponseEntity.ok().body(backgroundColorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /background-colors/:id} : get the "id" backgroundColor.
     *
     * @param id the id of the backgroundColorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the backgroundColorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/background-colors/{id}")
    public ResponseEntity<BackgroundColorDTO> getBackgroundColor(@PathVariable Long id) {
        log.debug("REST request to get BackgroundColor : {}", id);
        Optional<BackgroundColorDTO> backgroundColorDTO = backgroundColorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(backgroundColorDTO);
    }

    /**
     * {@code DELETE  /background-colors/:id} : delete the "id" backgroundColor.
     *
     * @param id the id of the backgroundColorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/background-colors/{id}")
    public ResponseEntity<Void> deleteBackgroundColor(@PathVariable Long id) {
        log.debug("REST request to delete BackgroundColor : {}", id);
        backgroundColorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
