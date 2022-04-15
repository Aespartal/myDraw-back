package com.project.mydraw.web.rest;

import com.project.mydraw.MydrawApp;
import com.project.mydraw.domain.LikeCommentary;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.domain.Commentary;
import com.project.mydraw.repository.LikeCommentaryRepository;
import com.project.mydraw.service.LikeCommentaryService;
import com.project.mydraw.service.dto.LikeCommentaryDTO;
import com.project.mydraw.service.mapper.LikeCommentaryMapper;
import com.project.mydraw.service.dto.LikeCommentaryCriteria;
import com.project.mydraw.service.LikeCommentaryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LikeCommentaryResource} REST controller.
 */
@SpringBootTest(classes = MydrawApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LikeCommentaryResourceIT {

    @Autowired
    private LikeCommentaryRepository likeCommentaryRepository;

    @Autowired
    private LikeCommentaryMapper likeCommentaryMapper;

    @Autowired
    private LikeCommentaryService likeCommentaryService;

    @Autowired
    private LikeCommentaryQueryService likeCommentaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikeCommentaryMockMvc;

    private LikeCommentary likeCommentary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeCommentary createEntity(EntityManager em) {
        LikeCommentary likeCommentary = new LikeCommentary();
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        likeCommentary.setExtendedUser(extendedUser);
        // Add required entity
        Commentary commentary;
        if (TestUtil.findAll(em, Commentary.class).isEmpty()) {
            commentary = CommentaryResourceIT.createEntity(em);
            em.persist(commentary);
            em.flush();
        } else {
            commentary = TestUtil.findAll(em, Commentary.class).get(0);
        }
        likeCommentary.setComentary(commentary);
        return likeCommentary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeCommentary createUpdatedEntity(EntityManager em) {
        LikeCommentary likeCommentary = new LikeCommentary();
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createUpdatedEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        likeCommentary.setExtendedUser(extendedUser);
        // Add required entity
        Commentary commentary;
        if (TestUtil.findAll(em, Commentary.class).isEmpty()) {
            commentary = CommentaryResourceIT.createUpdatedEntity(em);
            em.persist(commentary);
            em.flush();
        } else {
            commentary = TestUtil.findAll(em, Commentary.class).get(0);
        }
        likeCommentary.setComentary(commentary);
        return likeCommentary;
    }

    @BeforeEach
    public void initTest() {
        likeCommentary = createEntity(em);
    }

    @Test
    @Transactional
    public void createLikeCommentary() throws Exception {
        int databaseSizeBeforeCreate = likeCommentaryRepository.findAll().size();
        // Create the LikeCommentary
        LikeCommentaryDTO likeCommentaryDTO = likeCommentaryMapper.toDto(likeCommentary);
        restLikeCommentaryMockMvc.perform(post("/api/like-commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likeCommentaryDTO)))
            .andExpect(status().isCreated());

        // Validate the LikeCommentary in the database
        List<LikeCommentary> likeCommentaryList = likeCommentaryRepository.findAll();
        assertThat(likeCommentaryList).hasSize(databaseSizeBeforeCreate + 1);
        LikeCommentary testLikeCommentary = likeCommentaryList.get(likeCommentaryList.size() - 1);
    }

    @Test
    @Transactional
    public void createLikeCommentaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeCommentaryRepository.findAll().size();

        // Create the LikeCommentary with an existing ID
        likeCommentary.setId(1L);
        LikeCommentaryDTO likeCommentaryDTO = likeCommentaryMapper.toDto(likeCommentary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeCommentaryMockMvc.perform(post("/api/like-commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likeCommentaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LikeCommentary in the database
        List<LikeCommentary> likeCommentaryList = likeCommentaryRepository.findAll();
        assertThat(likeCommentaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLikeCommentaries() throws Exception {
        // Initialize the database
        likeCommentaryRepository.saveAndFlush(likeCommentary);

        // Get all the likeCommentaryList
        restLikeCommentaryMockMvc.perform(get("/api/like-commentaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeCommentary.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getLikeCommentary() throws Exception {
        // Initialize the database
        likeCommentaryRepository.saveAndFlush(likeCommentary);

        // Get the likeCommentary
        restLikeCommentaryMockMvc.perform(get("/api/like-commentaries/{id}", likeCommentary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(likeCommentary.getId().intValue()));
    }


    @Test
    @Transactional
    public void getLikeCommentariesByIdFiltering() throws Exception {
        // Initialize the database
        likeCommentaryRepository.saveAndFlush(likeCommentary);

        Long id = likeCommentary.getId();

        defaultLikeCommentaryShouldBeFound("id.equals=" + id);
        defaultLikeCommentaryShouldNotBeFound("id.notEquals=" + id);

        defaultLikeCommentaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLikeCommentaryShouldNotBeFound("id.greaterThan=" + id);

        defaultLikeCommentaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLikeCommentaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLikeCommentariesByExtendedUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        ExtendedUser extendedUser = likeCommentary.getExtendedUser();
        likeCommentaryRepository.saveAndFlush(likeCommentary);
        Long extendedUserId = extendedUser.getId();

        // Get all the likeCommentaryList where extendedUser equals to extendedUserId
        defaultLikeCommentaryShouldBeFound("extendedUserId.equals=" + extendedUserId);

        // Get all the likeCommentaryList where extendedUser equals to extendedUserId + 1
        defaultLikeCommentaryShouldNotBeFound("extendedUserId.equals=" + (extendedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllLikeCommentariesByComentaryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Commentary comentary = likeCommentary.getComentary();
        likeCommentaryRepository.saveAndFlush(likeCommentary);
        Long comentaryId = comentary.getId();

        // Get all the likeCommentaryList where comentary equals to comentaryId
        defaultLikeCommentaryShouldBeFound("comentaryId.equals=" + comentaryId);

        // Get all the likeCommentaryList where comentary equals to comentaryId + 1
        defaultLikeCommentaryShouldNotBeFound("comentaryId.equals=" + (comentaryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLikeCommentaryShouldBeFound(String filter) throws Exception {
        restLikeCommentaryMockMvc.perform(get("/api/like-commentaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeCommentary.getId().intValue())));

        // Check, that the count call also returns 1
        restLikeCommentaryMockMvc.perform(get("/api/like-commentaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLikeCommentaryShouldNotBeFound(String filter) throws Exception {
        restLikeCommentaryMockMvc.perform(get("/api/like-commentaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLikeCommentaryMockMvc.perform(get("/api/like-commentaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLikeCommentary() throws Exception {
        // Get the likeCommentary
        restLikeCommentaryMockMvc.perform(get("/api/like-commentaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLikeCommentary() throws Exception {
        // Initialize the database
        likeCommentaryRepository.saveAndFlush(likeCommentary);

        int databaseSizeBeforeUpdate = likeCommentaryRepository.findAll().size();

        // Update the likeCommentary
        LikeCommentary updatedLikeCommentary = likeCommentaryRepository.findById(likeCommentary.getId()).get();
        // Disconnect from session so that the updates on updatedLikeCommentary are not directly saved in db
        em.detach(updatedLikeCommentary);
        LikeCommentaryDTO likeCommentaryDTO = likeCommentaryMapper.toDto(updatedLikeCommentary);

        restLikeCommentaryMockMvc.perform(put("/api/like-commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likeCommentaryDTO)))
            .andExpect(status().isOk());

        // Validate the LikeCommentary in the database
        List<LikeCommentary> likeCommentaryList = likeCommentaryRepository.findAll();
        assertThat(likeCommentaryList).hasSize(databaseSizeBeforeUpdate);
        LikeCommentary testLikeCommentary = likeCommentaryList.get(likeCommentaryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLikeCommentary() throws Exception {
        int databaseSizeBeforeUpdate = likeCommentaryRepository.findAll().size();

        // Create the LikeCommentary
        LikeCommentaryDTO likeCommentaryDTO = likeCommentaryMapper.toDto(likeCommentary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeCommentaryMockMvc.perform(put("/api/like-commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likeCommentaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LikeCommentary in the database
        List<LikeCommentary> likeCommentaryList = likeCommentaryRepository.findAll();
        assertThat(likeCommentaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLikeCommentary() throws Exception {
        // Initialize the database
        likeCommentaryRepository.saveAndFlush(likeCommentary);

        int databaseSizeBeforeDelete = likeCommentaryRepository.findAll().size();

        // Delete the likeCommentary
        restLikeCommentaryMockMvc.perform(delete("/api/like-commentaries/{id}", likeCommentary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LikeCommentary> likeCommentaryList = likeCommentaryRepository.findAll();
        assertThat(likeCommentaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
