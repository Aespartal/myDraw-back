package com.project.mydraw.web.rest;

import com.project.mydraw.service.CommentaryService;
import com.project.mydraw.web.rest.errors.BadRequestAlertException;
import com.project.mydraw.service.dto.CommentaryDTO;
import com.project.mydraw.service.dto.CommentaryCriteria;
import com.project.mydraw.service.CommentaryQueryService;

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
 * REST controller for managing {@link com.project.mydraw.domain.Commentary}.
 */
@RestController
@RequestMapping("/api")
public class CommentaryResource {

    private final Logger log = LoggerFactory.getLogger(CommentaryResource.class);

    private static final String ENTITY_NAME = "commentary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentaryService commentaryService;

    private final CommentaryQueryService commentaryQueryService;

    public CommentaryResource(CommentaryService commentaryService, CommentaryQueryService commentaryQueryService) {
        this.commentaryService = commentaryService;
        this.commentaryQueryService = commentaryQueryService;
    }

    /**
     * {@code POST  /commentaries} : Create a new commentary.
     *
     * @param commentaryDTO the commentaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentaryDTO, or with status {@code 400 (Bad Request)} if the commentary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commentaries")
    public ResponseEntity<CommentaryDTO> createCommentary(@Valid @RequestBody CommentaryDTO commentaryDTO) throws URISyntaxException {
        log.debug("REST request to save Commentary : {}", commentaryDTO);
        if (commentaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentaryDTO result = commentaryService.save(commentaryDTO);
        return ResponseEntity.created(new URI("/api/commentaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commentaries} : Updates an existing commentary.
     *
     * @param commentaryDTO the commentaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentaryDTO,
     * or with status {@code 400 (Bad Request)} if the commentaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commentaries")
    public ResponseEntity<CommentaryDTO> updateCommentary(@Valid @RequestBody CommentaryDTO commentaryDTO) throws URISyntaxException {
        log.debug("REST request to update Commentary : {}", commentaryDTO);
        if (commentaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommentaryDTO result = commentaryService.save(commentaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commentaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commentaries} : get all the commentaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentaries in body.
     */
    @GetMapping("/commentaries")
    public ResponseEntity<List<CommentaryDTO>> getAllCommentaries(CommentaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Commentaries by criteria: {}", criteria);
        Page<CommentaryDTO> page = commentaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commentaries/count} : count all the commentaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commentaries/count")
    public ResponseEntity<Long> countCommentaries(CommentaryCriteria criteria) {
        log.debug("REST request to count Commentaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(commentaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commentaries/:id} : get the "id" commentary.
     *
     * @param id the id of the commentaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commentaries/{id}")
    public ResponseEntity<CommentaryDTO> getCommentary(@PathVariable Long id) {
        log.debug("REST request to get Commentary : {}", id);
        Optional<CommentaryDTO> commentaryDTO = commentaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentaryDTO);
    }

    /**
     * {@code DELETE  /commentaries/:id} : delete the "id" commentary.
     *
     * @param id the id of the commentaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commentaries/{id}")
    public ResponseEntity<Void> deleteCommentary(@PathVariable Long id) {
        log.debug("REST request to delete Commentary : {}", id);
        commentaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
