package com.project.mydraw.web.rest;

import com.project.mydraw.service.LikeAlbumService;
import com.project.mydraw.web.rest.errors.BadRequestAlertException;
import com.project.mydraw.service.dto.LikeAlbumDTO;
import com.project.mydraw.service.dto.LikeAlbumCriteria;
import com.project.mydraw.service.LikeAlbumQueryService;

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
 * REST controller for managing {@link com.project.mydraw.domain.LikeAlbum}.
 */
@RestController
@RequestMapping("/api")
public class LikeAlbumResource {

    private final Logger log = LoggerFactory.getLogger(LikeAlbumResource.class);

    private static final String ENTITY_NAME = "likeAlbum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeAlbumService likeAlbumService;

    private final LikeAlbumQueryService likeAlbumQueryService;

    public LikeAlbumResource(LikeAlbumService likeAlbumService, LikeAlbumQueryService likeAlbumQueryService) {
        this.likeAlbumService = likeAlbumService;
        this.likeAlbumQueryService = likeAlbumQueryService;
    }

    /**
     * {@code POST  /like-albums} : Create a new likeAlbum.
     *
     * @param likeAlbumDTO the likeAlbumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likeAlbumDTO, or with status {@code 400 (Bad Request)} if the likeAlbum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/like-albums")
    public ResponseEntity<LikeAlbumDTO> createLikeAlbum(@Valid @RequestBody LikeAlbumDTO likeAlbumDTO) throws URISyntaxException {
        log.debug("REST request to save LikeAlbum : {}", likeAlbumDTO);
        if (likeAlbumDTO.getId() != null) {
            throw new BadRequestAlertException("A new likeAlbum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikeAlbumDTO result = likeAlbumService.save(likeAlbumDTO);
        return ResponseEntity.created(new URI("/api/like-albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /like-albums} : Updates an existing likeAlbum.
     *
     * @param likeAlbumDTO the likeAlbumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeAlbumDTO,
     * or with status {@code 400 (Bad Request)} if the likeAlbumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likeAlbumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/like-albums")
    public ResponseEntity<LikeAlbumDTO> updateLikeAlbum(@Valid @RequestBody LikeAlbumDTO likeAlbumDTO) throws URISyntaxException {
        log.debug("REST request to update LikeAlbum : {}", likeAlbumDTO);
        if (likeAlbumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LikeAlbumDTO result = likeAlbumService.save(likeAlbumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, likeAlbumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /like-albums} : get all the likeAlbums.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likeAlbums in body.
     */
    @GetMapping("/like-albums")
    public ResponseEntity<List<LikeAlbumDTO>> getAllLikeAlbums(LikeAlbumCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LikeAlbums by criteria: {}", criteria);
        Page<LikeAlbumDTO> page = likeAlbumQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /like-albums/count} : count all the likeAlbums.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/like-albums/count")
    public ResponseEntity<Long> countLikeAlbums(LikeAlbumCriteria criteria) {
        log.debug("REST request to count LikeAlbums by criteria: {}", criteria);
        return ResponseEntity.ok().body(likeAlbumQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /like-albums/:id} : get the "id" likeAlbum.
     *
     * @param id the id of the likeAlbumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likeAlbumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/like-albums/{id}")
    public ResponseEntity<LikeAlbumDTO> getLikeAlbum(@PathVariable Long id) {
        log.debug("REST request to get LikeAlbum : {}", id);
        Optional<LikeAlbumDTO> likeAlbumDTO = likeAlbumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likeAlbumDTO);
    }

    /**
     * {@code DELETE  /like-albums/:id} : delete the "id" likeAlbum.
     *
     * @param id the id of the likeAlbumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/like-albums/{id}")
    public ResponseEntity<Void> deleteLikeAlbum(@PathVariable Long id) {
        log.debug("REST request to delete LikeAlbum : {}", id);
        likeAlbumService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
