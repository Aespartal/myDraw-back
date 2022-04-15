package com.project.mydraw.web.rest;

import com.project.mydraw.service.DisLikeCommentaryService;
import com.project.mydraw.web.rest.errors.BadRequestAlertException;
import com.project.mydraw.service.dto.DisLikeCommentaryDTO;
import com.project.mydraw.service.dto.DisLikeCommentaryCriteria;
import com.project.mydraw.service.DisLikeCommentaryQueryService;

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
 * REST controller for managing {@link com.project.mydraw.domain.DisLikeCommentary}.
 */
@RestController
@RequestMapping("/api")
public class DisLikeCommentaryResource {

    private final Logger log = LoggerFactory.getLogger(DisLikeCommentaryResource.class);

    private static final String ENTITY_NAME = "disLikeCommentary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisLikeCommentaryService disLikeCommentaryService;

    private final DisLikeCommentaryQueryService disLikeCommentaryQueryService;

    public DisLikeCommentaryResource(DisLikeCommentaryService disLikeCommentaryService, DisLikeCommentaryQueryService disLikeCommentaryQueryService) {
        this.disLikeCommentaryService = disLikeCommentaryService;
        this.disLikeCommentaryQueryService = disLikeCommentaryQueryService;
    }

    /**
     * {@code POST  /dis-like-commentaries} : Create a new disLikeCommentary.
     *
     * @param disLikeCommentaryDTO the disLikeCommentaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disLikeCommentaryDTO, or with status {@code 400 (Bad Request)} if the disLikeCommentary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dis-like-commentaries")
    public ResponseEntity<DisLikeCommentaryDTO> createDisLikeCommentary(@Valid @RequestBody DisLikeCommentaryDTO disLikeCommentaryDTO) throws URISyntaxException {
        log.debug("REST request to save DisLikeCommentary : {}", disLikeCommentaryDTO);
        if (disLikeCommentaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new disLikeCommentary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisLikeCommentaryDTO result = disLikeCommentaryService.save(disLikeCommentaryDTO);
        return ResponseEntity.created(new URI("/api/dis-like-commentaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dis-like-commentaries} : Updates an existing disLikeCommentary.
     *
     * @param disLikeCommentaryDTO the disLikeCommentaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disLikeCommentaryDTO,
     * or with status {@code 400 (Bad Request)} if the disLikeCommentaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disLikeCommentaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dis-like-commentaries")
    public ResponseEntity<DisLikeCommentaryDTO> updateDisLikeCommentary(@Valid @RequestBody DisLikeCommentaryDTO disLikeCommentaryDTO) throws URISyntaxException {
        log.debug("REST request to update DisLikeCommentary : {}", disLikeCommentaryDTO);
        if (disLikeCommentaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisLikeCommentaryDTO result = disLikeCommentaryService.save(disLikeCommentaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, disLikeCommentaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dis-like-commentaries} : get all the disLikeCommentaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disLikeCommentaries in body.
     */
    @GetMapping("/dis-like-commentaries")
    public ResponseEntity<List<DisLikeCommentaryDTO>> getAllDisLikeCommentaries(DisLikeCommentaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DisLikeCommentaries by criteria: {}", criteria);
        Page<DisLikeCommentaryDTO> page = disLikeCommentaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dis-like-commentaries/count} : count all the disLikeCommentaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dis-like-commentaries/count")
    public ResponseEntity<Long> countDisLikeCommentaries(DisLikeCommentaryCriteria criteria) {
        log.debug("REST request to count DisLikeCommentaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(disLikeCommentaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dis-like-commentaries/:id} : get the "id" disLikeCommentary.
     *
     * @param id the id of the disLikeCommentaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disLikeCommentaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dis-like-commentaries/{id}")
    public ResponseEntity<DisLikeCommentaryDTO> getDisLikeCommentary(@PathVariable Long id) {
        log.debug("REST request to get DisLikeCommentary : {}", id);
        Optional<DisLikeCommentaryDTO> disLikeCommentaryDTO = disLikeCommentaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disLikeCommentaryDTO);
    }

    /**
     * {@code DELETE  /dis-like-commentaries/:id} : delete the "id" disLikeCommentary.
     *
     * @param id the id of the disLikeCommentaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dis-like-commentaries/{id}")
    public ResponseEntity<Void> deleteDisLikeCommentary(@PathVariable Long id) {
        log.debug("REST request to delete DisLikeCommentary : {}", id);
        disLikeCommentaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
