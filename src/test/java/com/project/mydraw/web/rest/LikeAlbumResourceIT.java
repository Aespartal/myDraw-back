package com.project.mydraw.web.rest;

import com.project.mydraw.MydrawApp;
import com.project.mydraw.domain.LikeAlbum;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.domain.Album;
import com.project.mydraw.repository.LikeAlbumRepository;
import com.project.mydraw.service.LikeAlbumService;
import com.project.mydraw.service.dto.LikeAlbumDTO;
import com.project.mydraw.service.mapper.LikeAlbumMapper;
import com.project.mydraw.service.dto.LikeAlbumCriteria;
import com.project.mydraw.service.LikeAlbumQueryService;

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
 * Integration tests for the {@link LikeAlbumResource} REST controller.
 */
@SpringBootTest(classes = MydrawApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LikeAlbumResourceIT {

    @Autowired
    private LikeAlbumRepository likeAlbumRepository;

    @Autowired
    private LikeAlbumMapper likeAlbumMapper;

    @Autowired
    private LikeAlbumService likeAlbumService;

    @Autowired
    private LikeAlbumQueryService likeAlbumQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikeAlbumMockMvc;

    private LikeAlbum likeAlbum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeAlbum createEntity(EntityManager em) {
        LikeAlbum likeAlbum = new LikeAlbum();
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        likeAlbum.setExtendedUser(extendedUser);
        // Add required entity
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            album = AlbumResourceIT.createEntity(em);
            em.persist(album);
            em.flush();
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        likeAlbum.setAlbum(album);
        return likeAlbum;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeAlbum createUpdatedEntity(EntityManager em) {
        LikeAlbum likeAlbum = new LikeAlbum();
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createUpdatedEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        likeAlbum.setExtendedUser(extendedUser);
        // Add required entity
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            album = AlbumResourceIT.createUpdatedEntity(em);
            em.persist(album);
            em.flush();
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        likeAlbum.setAlbum(album);
        return likeAlbum;
    }

    @BeforeEach
    public void initTest() {
        likeAlbum = createEntity(em);
    }

    @Test
    @Transactional
    public void createLikeAlbum() throws Exception {
        int databaseSizeBeforeCreate = likeAlbumRepository.findAll().size();
        // Create the LikeAlbum
        LikeAlbumDTO likeAlbumDTO = likeAlbumMapper.toDto(likeAlbum);
        restLikeAlbumMockMvc.perform(post("/api/like-albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likeAlbumDTO)))
            .andExpect(status().isCreated());

        // Validate the LikeAlbum in the database
        List<LikeAlbum> likeAlbumList = likeAlbumRepository.findAll();
        assertThat(likeAlbumList).hasSize(databaseSizeBeforeCreate + 1);
        LikeAlbum testLikeAlbum = likeAlbumList.get(likeAlbumList.size() - 1);
    }

    @Test
    @Transactional
    public void createLikeAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeAlbumRepository.findAll().size();

        // Create the LikeAlbum with an existing ID
        likeAlbum.setId(1L);
        LikeAlbumDTO likeAlbumDTO = likeAlbumMapper.toDto(likeAlbum);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeAlbumMockMvc.perform(post("/api/like-albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likeAlbumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LikeAlbum in the database
        List<LikeAlbum> likeAlbumList = likeAlbumRepository.findAll();
        assertThat(likeAlbumList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLikeAlbums() throws Exception {
        // Initialize the database
        likeAlbumRepository.saveAndFlush(likeAlbum);

        // Get all the likeAlbumList
        restLikeAlbumMockMvc.perform(get("/api/like-albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeAlbum.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getLikeAlbum() throws Exception {
        // Initialize the database
        likeAlbumRepository.saveAndFlush(likeAlbum);

        // Get the likeAlbum
        restLikeAlbumMockMvc.perform(get("/api/like-albums/{id}", likeAlbum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(likeAlbum.getId().intValue()));
    }


    @Test
    @Transactional
    public void getLikeAlbumsByIdFiltering() throws Exception {
        // Initialize the database
        likeAlbumRepository.saveAndFlush(likeAlbum);

        Long id = likeAlbum.getId();

        defaultLikeAlbumShouldBeFound("id.equals=" + id);
        defaultLikeAlbumShouldNotBeFound("id.notEquals=" + id);

        defaultLikeAlbumShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLikeAlbumShouldNotBeFound("id.greaterThan=" + id);

        defaultLikeAlbumShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLikeAlbumShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLikeAlbumsByExtendedUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        ExtendedUser extendedUser = likeAlbum.getExtendedUser();
        likeAlbumRepository.saveAndFlush(likeAlbum);
        Long extendedUserId = extendedUser.getId();

        // Get all the likeAlbumList where extendedUser equals to extendedUserId
        defaultLikeAlbumShouldBeFound("extendedUserId.equals=" + extendedUserId);

        // Get all the likeAlbumList where extendedUser equals to extendedUserId + 1
        defaultLikeAlbumShouldNotBeFound("extendedUserId.equals=" + (extendedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllLikeAlbumsByAlbumIsEqualToSomething() throws Exception {
        // Get already existing entity
        Album album = likeAlbum.getAlbum();
        likeAlbumRepository.saveAndFlush(likeAlbum);
        Long albumId = album.getId();

        // Get all the likeAlbumList where album equals to albumId
        defaultLikeAlbumShouldBeFound("albumId.equals=" + albumId);

        // Get all the likeAlbumList where album equals to albumId + 1
        defaultLikeAlbumShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLikeAlbumShouldBeFound(String filter) throws Exception {
        restLikeAlbumMockMvc.perform(get("/api/like-albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeAlbum.getId().intValue())));

        // Check, that the count call also returns 1
        restLikeAlbumMockMvc.perform(get("/api/like-albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLikeAlbumShouldNotBeFound(String filter) throws Exception {
        restLikeAlbumMockMvc.perform(get("/api/like-albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLikeAlbumMockMvc.perform(get("/api/like-albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLikeAlbum() throws Exception {
        // Get the likeAlbum
        restLikeAlbumMockMvc.perform(get("/api/like-albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLikeAlbum() throws Exception {
        // Initialize the database
        likeAlbumRepository.saveAndFlush(likeAlbum);

        int databaseSizeBeforeUpdate = likeAlbumRepository.findAll().size();

        // Update the likeAlbum
        LikeAlbum updatedLikeAlbum = likeAlbumRepository.findById(likeAlbum.getId()).get();
        // Disconnect from session so that the updates on updatedLikeAlbum are not directly saved in db
        em.detach(updatedLikeAlbum);
        LikeAlbumDTO likeAlbumDTO = likeAlbumMapper.toDto(updatedLikeAlbum);

        restLikeAlbumMockMvc.perform(put("/api/like-albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likeAlbumDTO)))
            .andExpect(status().isOk());

        // Validate the LikeAlbum in the database
        List<LikeAlbum> likeAlbumList = likeAlbumRepository.findAll();
        assertThat(likeAlbumList).hasSize(databaseSizeBeforeUpdate);
        LikeAlbum testLikeAlbum = likeAlbumList.get(likeAlbumList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLikeAlbum() throws Exception {
        int databaseSizeBeforeUpdate = likeAlbumRepository.findAll().size();

        // Create the LikeAlbum
        LikeAlbumDTO likeAlbumDTO = likeAlbumMapper.toDto(likeAlbum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeAlbumMockMvc.perform(put("/api/like-albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likeAlbumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LikeAlbum in the database
        List<LikeAlbum> likeAlbumList = likeAlbumRepository.findAll();
        assertThat(likeAlbumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLikeAlbum() throws Exception {
        // Initialize the database
        likeAlbumRepository.saveAndFlush(likeAlbum);

        int databaseSizeBeforeDelete = likeAlbumRepository.findAll().size();

        // Delete the likeAlbum
        restLikeAlbumMockMvc.perform(delete("/api/like-albums/{id}", likeAlbum.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LikeAlbum> likeAlbumList = likeAlbumRepository.findAll();
        assertThat(likeAlbumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
