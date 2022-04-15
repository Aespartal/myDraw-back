package com.project.mydraw.web.rest;

import com.project.mydraw.MydrawApp;
import com.project.mydraw.domain.Commentary;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.domain.Album;
import com.project.mydraw.repository.CommentaryRepository;
import com.project.mydraw.service.CommentaryService;
import com.project.mydraw.service.dto.CommentaryDTO;
import com.project.mydraw.service.mapper.CommentaryMapper;
import com.project.mydraw.service.dto.CommentaryCriteria;
import com.project.mydraw.service.CommentaryQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommentaryResource} REST controller.
 */
@SpringBootTest(classes = MydrawApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommentaryResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_MODIFIED = false;
    private static final Boolean UPDATED_IS_MODIFIED = true;

    @Autowired
    private CommentaryRepository commentaryRepository;

    @Autowired
    private CommentaryMapper commentaryMapper;

    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private CommentaryQueryService commentaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentaryMockMvc;

    private Commentary commentary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commentary createEntity(EntityManager em) {
        Commentary commentary = new Commentary()
            .description(DEFAULT_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .isModified(DEFAULT_IS_MODIFIED);
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        commentary.setExtendedUser(extendedUser);
        // Add required entity
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            album = AlbumResourceIT.createEntity(em);
            em.persist(album);
            em.flush();
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        commentary.setAlbum(album);
        return commentary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commentary createUpdatedEntity(EntityManager em) {
        Commentary commentary = new Commentary()
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .isModified(UPDATED_IS_MODIFIED);
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createUpdatedEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        commentary.setExtendedUser(extendedUser);
        // Add required entity
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            album = AlbumResourceIT.createUpdatedEntity(em);
            em.persist(album);
            em.flush();
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        commentary.setAlbum(album);
        return commentary;
    }

    @BeforeEach
    public void initTest() {
        commentary = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentary() throws Exception {
        int databaseSizeBeforeCreate = commentaryRepository.findAll().size();
        // Create the Commentary
        CommentaryDTO commentaryDTO = commentaryMapper.toDto(commentary);
        restCommentaryMockMvc.perform(post("/api/commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentaryDTO)))
            .andExpect(status().isCreated());

        // Validate the Commentary in the database
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeCreate + 1);
        Commentary testCommentary = commentaryList.get(commentaryList.size() - 1);
        assertThat(testCommentary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCommentary.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCommentary.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testCommentary.isIsModified()).isEqualTo(DEFAULT_IS_MODIFIED);
    }

    @Test
    @Transactional
    public void createCommentaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentaryRepository.findAll().size();

        // Create the Commentary with an existing ID
        commentary.setId(1L);
        CommentaryDTO commentaryDTO = commentaryMapper.toDto(commentary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentaryMockMvc.perform(post("/api/commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commentary in the database
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentaryRepository.findAll().size();
        // set the field null
        commentary.setDescription(null);

        // Create the Commentary, which fails.
        CommentaryDTO commentaryDTO = commentaryMapper.toDto(commentary);


        restCommentaryMockMvc.perform(post("/api/commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentaryDTO)))
            .andExpect(status().isBadRequest());

        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentaryRepository.findAll().size();
        // set the field null
        commentary.setDateCreated(null);

        // Create the Commentary, which fails.
        CommentaryDTO commentaryDTO = commentaryMapper.toDto(commentary);


        restCommentaryMockMvc.perform(post("/api/commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentaryDTO)))
            .andExpect(status().isBadRequest());

        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommentaries() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList
        restCommentaryMockMvc.perform(get("/api/commentaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentary.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].isModified").value(hasItem(DEFAULT_IS_MODIFIED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCommentary() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get the commentary
        restCommentaryMockMvc.perform(get("/api/commentaries/{id}", commentary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commentary.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.isModified").value(DEFAULT_IS_MODIFIED.booleanValue()));
    }


    @Test
    @Transactional
    public void getCommentariesByIdFiltering() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        Long id = commentary.getId();

        defaultCommentaryShouldBeFound("id.equals=" + id);
        defaultCommentaryShouldNotBeFound("id.notEquals=" + id);

        defaultCommentaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommentaryShouldNotBeFound("id.greaterThan=" + id);

        defaultCommentaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommentaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommentariesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where description equals to DEFAULT_DESCRIPTION
        defaultCommentaryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the commentaryList where description equals to UPDATED_DESCRIPTION
        defaultCommentaryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where description not equals to DEFAULT_DESCRIPTION
        defaultCommentaryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the commentaryList where description not equals to UPDATED_DESCRIPTION
        defaultCommentaryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCommentaryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the commentaryList where description equals to UPDATED_DESCRIPTION
        defaultCommentaryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where description is not null
        defaultCommentaryShouldBeFound("description.specified=true");

        // Get all the commentaryList where description is null
        defaultCommentaryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommentariesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where description contains DEFAULT_DESCRIPTION
        defaultCommentaryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the commentaryList where description contains UPDATED_DESCRIPTION
        defaultCommentaryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where description does not contain DEFAULT_DESCRIPTION
        defaultCommentaryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the commentaryList where description does not contain UPDATED_DESCRIPTION
        defaultCommentaryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCommentariesByDateCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where dateCreated equals to DEFAULT_DATE_CREATED
        defaultCommentaryShouldBeFound("dateCreated.equals=" + DEFAULT_DATE_CREATED);

        // Get all the commentaryList where dateCreated equals to UPDATED_DATE_CREATED
        defaultCommentaryShouldNotBeFound("dateCreated.equals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDateCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where dateCreated not equals to DEFAULT_DATE_CREATED
        defaultCommentaryShouldNotBeFound("dateCreated.notEquals=" + DEFAULT_DATE_CREATED);

        // Get all the commentaryList where dateCreated not equals to UPDATED_DATE_CREATED
        defaultCommentaryShouldBeFound("dateCreated.notEquals=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDateCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where dateCreated in DEFAULT_DATE_CREATED or UPDATED_DATE_CREATED
        defaultCommentaryShouldBeFound("dateCreated.in=" + DEFAULT_DATE_CREATED + "," + UPDATED_DATE_CREATED);

        // Get all the commentaryList where dateCreated equals to UPDATED_DATE_CREATED
        defaultCommentaryShouldNotBeFound("dateCreated.in=" + UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDateCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where dateCreated is not null
        defaultCommentaryShouldBeFound("dateCreated.specified=true");

        // Get all the commentaryList where dateCreated is null
        defaultCommentaryShouldNotBeFound("dateCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommentariesByDateModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where dateModified equals to DEFAULT_DATE_MODIFIED
        defaultCommentaryShouldBeFound("dateModified.equals=" + DEFAULT_DATE_MODIFIED);

        // Get all the commentaryList where dateModified equals to UPDATED_DATE_MODIFIED
        defaultCommentaryShouldNotBeFound("dateModified.equals=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDateModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where dateModified not equals to DEFAULT_DATE_MODIFIED
        defaultCommentaryShouldNotBeFound("dateModified.notEquals=" + DEFAULT_DATE_MODIFIED);

        // Get all the commentaryList where dateModified not equals to UPDATED_DATE_MODIFIED
        defaultCommentaryShouldBeFound("dateModified.notEquals=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDateModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where dateModified in DEFAULT_DATE_MODIFIED or UPDATED_DATE_MODIFIED
        defaultCommentaryShouldBeFound("dateModified.in=" + DEFAULT_DATE_MODIFIED + "," + UPDATED_DATE_MODIFIED);

        // Get all the commentaryList where dateModified equals to UPDATED_DATE_MODIFIED
        defaultCommentaryShouldNotBeFound("dateModified.in=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByDateModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where dateModified is not null
        defaultCommentaryShouldBeFound("dateModified.specified=true");

        // Get all the commentaryList where dateModified is null
        defaultCommentaryShouldNotBeFound("dateModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommentariesByIsModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where isModified equals to DEFAULT_IS_MODIFIED
        defaultCommentaryShouldBeFound("isModified.equals=" + DEFAULT_IS_MODIFIED);

        // Get all the commentaryList where isModified equals to UPDATED_IS_MODIFIED
        defaultCommentaryShouldNotBeFound("isModified.equals=" + UPDATED_IS_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByIsModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where isModified not equals to DEFAULT_IS_MODIFIED
        defaultCommentaryShouldNotBeFound("isModified.notEquals=" + DEFAULT_IS_MODIFIED);

        // Get all the commentaryList where isModified not equals to UPDATED_IS_MODIFIED
        defaultCommentaryShouldBeFound("isModified.notEquals=" + UPDATED_IS_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByIsModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where isModified in DEFAULT_IS_MODIFIED or UPDATED_IS_MODIFIED
        defaultCommentaryShouldBeFound("isModified.in=" + DEFAULT_IS_MODIFIED + "," + UPDATED_IS_MODIFIED);

        // Get all the commentaryList where isModified equals to UPDATED_IS_MODIFIED
        defaultCommentaryShouldNotBeFound("isModified.in=" + UPDATED_IS_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllCommentariesByIsModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        // Get all the commentaryList where isModified is not null
        defaultCommentaryShouldBeFound("isModified.specified=true");

        // Get all the commentaryList where isModified is null
        defaultCommentaryShouldNotBeFound("isModified.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommentariesByExtendedUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        ExtendedUser extendedUser = commentary.getExtendedUser();
        commentaryRepository.saveAndFlush(commentary);
        Long extendedUserId = extendedUser.getId();

        // Get all the commentaryList where extendedUser equals to extendedUserId
        defaultCommentaryShouldBeFound("extendedUserId.equals=" + extendedUserId);

        // Get all the commentaryList where extendedUser equals to extendedUserId + 1
        defaultCommentaryShouldNotBeFound("extendedUserId.equals=" + (extendedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllCommentariesByAlbumIsEqualToSomething() throws Exception {
        // Get already existing entity
        Album album = commentary.getAlbum();
        commentaryRepository.saveAndFlush(commentary);
        Long albumId = album.getId();

        // Get all the commentaryList where album equals to albumId
        defaultCommentaryShouldBeFound("albumId.equals=" + albumId);

        // Get all the commentaryList where album equals to albumId + 1
        defaultCommentaryShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommentaryShouldBeFound(String filter) throws Exception {
        restCommentaryMockMvc.perform(get("/api/commentaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentary.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].isModified").value(hasItem(DEFAULT_IS_MODIFIED.booleanValue())));

        // Check, that the count call also returns 1
        restCommentaryMockMvc.perform(get("/api/commentaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommentaryShouldNotBeFound(String filter) throws Exception {
        restCommentaryMockMvc.perform(get("/api/commentaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommentaryMockMvc.perform(get("/api/commentaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCommentary() throws Exception {
        // Get the commentary
        restCommentaryMockMvc.perform(get("/api/commentaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentary() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        int databaseSizeBeforeUpdate = commentaryRepository.findAll().size();

        // Update the commentary
        Commentary updatedCommentary = commentaryRepository.findById(commentary.getId()).get();
        // Disconnect from session so that the updates on updatedCommentary are not directly saved in db
        em.detach(updatedCommentary);
        updatedCommentary
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .isModified(UPDATED_IS_MODIFIED);
        CommentaryDTO commentaryDTO = commentaryMapper.toDto(updatedCommentary);

        restCommentaryMockMvc.perform(put("/api/commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentaryDTO)))
            .andExpect(status().isOk());

        // Validate the Commentary in the database
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeUpdate);
        Commentary testCommentary = commentaryList.get(commentaryList.size() - 1);
        assertThat(testCommentary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCommentary.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCommentary.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCommentary.isIsModified()).isEqualTo(UPDATED_IS_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentary() throws Exception {
        int databaseSizeBeforeUpdate = commentaryRepository.findAll().size();

        // Create the Commentary
        CommentaryDTO commentaryDTO = commentaryMapper.toDto(commentary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentaryMockMvc.perform(put("/api/commentaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commentaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commentary in the database
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommentary() throws Exception {
        // Initialize the database
        commentaryRepository.saveAndFlush(commentary);

        int databaseSizeBeforeDelete = commentaryRepository.findAll().size();

        // Delete the commentary
        restCommentaryMockMvc.perform(delete("/api/commentaries/{id}", commentary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commentary> commentaryList = commentaryRepository.findAll();
        assertThat(commentaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
