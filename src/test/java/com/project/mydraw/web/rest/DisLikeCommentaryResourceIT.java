package com.project.mydraw.web.rest;

import com.project.mydraw.MydrawApp;
import com.project.mydraw.domain.DisLikeCommentary;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.domain.Commentary;
import com.project.mydraw.repository.DisLikeCommentaryRepository;
import com.project.mydraw.service.DisLikeCommentaryService;
import com.project.mydraw.service.dto.DisLikeCommentaryDTO;
import com.project.mydraw.service.mapper.DisLikeCommentaryMapper;
import com.project.mydraw.service.dto.DisLikeCommentaryCriteria;
import com.project.mydraw.service.DisLikeCommentaryQueryService;

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
 * Integration tests for the {@link DisLikeCommentaryResource} REST controller.
 */
@SpringBootTest(classes = MydrawApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DisLikeCommentaryResourceIT {

    @Autowired
    private DisLikeCommentaryRepository disLikeCommentaryRepository;

    @Autowired
    private DisLikeCommentaryMapper disLikeCommentaryMapper;

    @Autowired
    private DisLikeCommentaryService disLikeCommentaryService;

    @Autowired
    private DisLikeCommentaryQueryService disLikeCommentaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisLikeCommentaryMockMvc;

    private DisLikeCommentary disLikeCommentary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisLikeCommentary createEntity(EntityManager em) {
        DisLikeCommentary disLikeCommentary = new DisLikeCommentary();
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        disLikeCommentary.setExtendedUser(extendedUser);
        // Add required entity
        Commentary commentary;
        if (TestUtil.findAll(em, Commentary.class).isEmpty()) {
            commentary = CommentaryResourceIT.createEntity(em);
            em.persist(commentary);
            em.flush();
        } else {
            commentary = TestUtil.findAll(em, Commentary.class).get(0);
        }
        disLikeCommentary.setComentary(commentary);
        return disLikeCommentary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisLikeCommentary createUpdatedEntity(EntityManager em) {
        DisLikeCommentary disLikeCommentary = new DisLikeCommentary();
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createUpdatedEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        disLikeCommentary.setExtendedUser(extendedUser);
        // Add required entity
        Commentary commentary;
        if (TestUtil.findAll(em, Commentary.class).isEmpty()) {
            commentary = CommentaryResourceIT.createUpdatedEntity(em);
            em.persist(commentary);
            em.flush();
        } else {
            commentary = TestUtil.findAll(em, Commentary.class).get(0);
        }
        disLikeCommentary.setComentary(commentary);
        return disLikeCommentary;
    }

    @BeforeEach
    public void initTest() {
        disLikeCommentary = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisLikeCommentary() throws Exception {
        int databaseSizeBeforeCreate = disLikeCommentaryRepository.findAll().size();
        // Create the DisLikeCommentary
        DisLikeCommentaryDTO disLikeCommentaryDTO = disLikeCommentaryMapper.toDto(disLikeCommentary);
        restDisLikeCommentaryMockMvc.perform(post("/api/dis-like-commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disLikeCommentaryDTO)))
            .andExpect(status().isCreated());

        // Validate the DisLikeCommentary in the database
        List<DisLikeCommentary> disLikeCommentaryList = disLikeCommentaryRepository.findAll();
        assertThat(disLikeCommentaryList).hasSize(databaseSizeBeforeCreate + 1);
        DisLikeCommentary testDisLikeCommentary = disLikeCommentaryList.get(disLikeCommentaryList.size() - 1);
    }

    @Test
    @Transactional
    public void createDisLikeCommentaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disLikeCommentaryRepository.findAll().size();

        // Create the DisLikeCommentary with an existing ID
        disLikeCommentary.setId(1L);
        DisLikeCommentaryDTO disLikeCommentaryDTO = disLikeCommentaryMapper.toDto(disLikeCommentary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisLikeCommentaryMockMvc.perform(post("/api/dis-like-commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disLikeCommentaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DisLikeCommentary in the database
        List<DisLikeCommentary> disLikeCommentaryList = disLikeCommentaryRepository.findAll();
        assertThat(disLikeCommentaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDisLikeCommentaries() throws Exception {
        // Initialize the database
        disLikeCommentaryRepository.saveAndFlush(disLikeCommentary);

        // Get all the disLikeCommentaryList
        restDisLikeCommentaryMockMvc.perform(get("/api/dis-like-commentaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disLikeCommentary.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDisLikeCommentary() throws Exception {
        // Initialize the database
        disLikeCommentaryRepository.saveAndFlush(disLikeCommentary);

        // Get the disLikeCommentary
        restDisLikeCommentaryMockMvc.perform(get("/api/dis-like-commentaries/{id}", disLikeCommentary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disLikeCommentary.getId().intValue()));
    }


    @Test
    @Transactional
    public void getDisLikeCommentariesByIdFiltering() throws Exception {
        // Initialize the database
        disLikeCommentaryRepository.saveAndFlush(disLikeCommentary);

        Long id = disLikeCommentary.getId();

        defaultDisLikeCommentaryShouldBeFound("id.equals=" + id);
        defaultDisLikeCommentaryShouldNotBeFound("id.notEquals=" + id);

        defaultDisLikeCommentaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDisLikeCommentaryShouldNotBeFound("id.greaterThan=" + id);

        defaultDisLikeCommentaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDisLikeCommentaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDisLikeCommentariesByExtendedUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        ExtendedUser extendedUser = disLikeCommentary.getExtendedUser();
        disLikeCommentaryRepository.saveAndFlush(disLikeCommentary);
        Long extendedUserId = extendedUser.getId();

        // Get all the disLikeCommentaryList where extendedUser equals to extendedUserId
        defaultDisLikeCommentaryShouldBeFound("extendedUserId.equals=" + extendedUserId);

        // Get all the disLikeCommentaryList where extendedUser equals to extendedUserId + 1
        defaultDisLikeCommentaryShouldNotBeFound("extendedUserId.equals=" + (extendedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllDisLikeCommentariesByComentaryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Commentary comentary = disLikeCommentary.getComentary();
        disLikeCommentaryRepository.saveAndFlush(disLikeCommentary);
        Long comentaryId = comentary.getId();

        // Get all the disLikeCommentaryList where comentary equals to comentaryId
        defaultDisLikeCommentaryShouldBeFound("comentaryId.equals=" + comentaryId);

        // Get all the disLikeCommentaryList where comentary equals to comentaryId + 1
        defaultDisLikeCommentaryShouldNotBeFound("comentaryId.equals=" + (comentaryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisLikeCommentaryShouldBeFound(String filter) throws Exception {
        restDisLikeCommentaryMockMvc.perform(get("/api/dis-like-commentaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disLikeCommentary.getId().intValue())));

        // Check, that the count call also returns 1
        restDisLikeCommentaryMockMvc.perform(get("/api/dis-like-commentaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisLikeCommentaryShouldNotBeFound(String filter) throws Exception {
        restDisLikeCommentaryMockMvc.perform(get("/api/dis-like-commentaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisLikeCommentaryMockMvc.perform(get("/api/dis-like-commentaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDisLikeCommentary() throws Exception {
        // Get the disLikeCommentary
        restDisLikeCommentaryMockMvc.perform(get("/api/dis-like-commentaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisLikeCommentary() throws Exception {
        // Initialize the database
        disLikeCommentaryRepository.saveAndFlush(disLikeCommentary);

        int databaseSizeBeforeUpdate = disLikeCommentaryRepository.findAll().size();

        // Update the disLikeCommentary
        DisLikeCommentary updatedDisLikeCommentary = disLikeCommentaryRepository.findById(disLikeCommentary.getId()).get();
        // Disconnect from session so that the updates on updatedDisLikeCommentary are not directly saved in db
        em.detach(updatedDisLikeCommentary);
        DisLikeCommentaryDTO disLikeCommentaryDTO = disLikeCommentaryMapper.toDto(updatedDisLikeCommentary);

        restDisLikeCommentaryMockMvc.perform(put("/api/dis-like-commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disLikeCommentaryDTO)))
            .andExpect(status().isOk());

        // Validate the DisLikeCommentary in the database
        List<DisLikeCommentary> disLikeCommentaryList = disLikeCommentaryRepository.findAll();
        assertThat(disLikeCommentaryList).hasSize(databaseSizeBeforeUpdate);
        DisLikeCommentary testDisLikeCommentary = disLikeCommentaryList.get(disLikeCommentaryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDisLikeCommentary() throws Exception {
        int databaseSizeBeforeUpdate = disLikeCommentaryRepository.findAll().size();

        // Create the DisLikeCommentary
        DisLikeCommentaryDTO disLikeCommentaryDTO = disLikeCommentaryMapper.toDto(disLikeCommentary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisLikeCommentaryMockMvc.perform(put("/api/dis-like-commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disLikeCommentaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DisLikeCommentary in the database
        List<DisLikeCommentary> disLikeCommentaryList = disLikeCommentaryRepository.findAll();
        assertThat(disLikeCommentaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisLikeCommentary() throws Exception {
        // Initialize the database
        disLikeCommentaryRepository.saveAndFlush(disLikeCommentary);

        int databaseSizeBeforeDelete = disLikeCommentaryRepository.findAll().size();

        // Delete the disLikeCommentary
        restDisLikeCommentaryMockMvc.perform(delete("/api/dis-like-commentaries/{id}", disLikeCommentary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DisLikeCommentary> disLikeCommentaryList = disLikeCommentaryRepository.findAll();
        assertThat(disLikeCommentaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
