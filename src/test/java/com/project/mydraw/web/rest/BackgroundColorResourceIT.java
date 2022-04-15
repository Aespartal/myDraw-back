package com.project.mydraw.web.rest;

import com.project.mydraw.MydrawApp;
import com.project.mydraw.domain.BackgroundColor;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.repository.BackgroundColorRepository;
import com.project.mydraw.service.BackgroundColorService;
import com.project.mydraw.service.dto.BackgroundColorDTO;
import com.project.mydraw.service.mapper.BackgroundColorMapper;
import com.project.mydraw.service.dto.BackgroundColorCriteria;
import com.project.mydraw.service.BackgroundColorQueryService;

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
 * Integration tests for the {@link BackgroundColorResource} REST controller.
 */
@SpringBootTest(classes = MydrawApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BackgroundColorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private BackgroundColorRepository backgroundColorRepository;

    @Autowired
    private BackgroundColorMapper backgroundColorMapper;

    @Autowired
    private BackgroundColorService backgroundColorService;

    @Autowired
    private BackgroundColorQueryService backgroundColorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBackgroundColorMockMvc;

    private BackgroundColor backgroundColor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BackgroundColor createEntity(EntityManager em) {
        BackgroundColor backgroundColor = new BackgroundColor()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return backgroundColor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BackgroundColor createUpdatedEntity(EntityManager em) {
        BackgroundColor backgroundColor = new BackgroundColor()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);
        return backgroundColor;
    }

    @BeforeEach
    public void initTest() {
        backgroundColor = createEntity(em);
    }

    @Test
    @Transactional
    public void createBackgroundColor() throws Exception {
        int databaseSizeBeforeCreate = backgroundColorRepository.findAll().size();
        // Create the BackgroundColor
        BackgroundColorDTO backgroundColorDTO = backgroundColorMapper.toDto(backgroundColor);
        restBackgroundColorMockMvc.perform(post("/api/background-colors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backgroundColorDTO)))
            .andExpect(status().isCreated());

        // Validate the BackgroundColor in the database
        List<BackgroundColor> backgroundColorList = backgroundColorRepository.findAll();
        assertThat(backgroundColorList).hasSize(databaseSizeBeforeCreate + 1);
        BackgroundColor testBackgroundColor = backgroundColorList.get(backgroundColorList.size() - 1);
        assertThat(testBackgroundColor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBackgroundColor.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createBackgroundColorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = backgroundColorRepository.findAll().size();

        // Create the BackgroundColor with an existing ID
        backgroundColor.setId(1L);
        BackgroundColorDTO backgroundColorDTO = backgroundColorMapper.toDto(backgroundColor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBackgroundColorMockMvc.perform(post("/api/background-colors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backgroundColorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BackgroundColor in the database
        List<BackgroundColor> backgroundColorList = backgroundColorRepository.findAll();
        assertThat(backgroundColorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = backgroundColorRepository.findAll().size();
        // set the field null
        backgroundColor.setName(null);

        // Create the BackgroundColor, which fails.
        BackgroundColorDTO backgroundColorDTO = backgroundColorMapper.toDto(backgroundColor);


        restBackgroundColorMockMvc.perform(post("/api/background-colors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backgroundColorDTO)))
            .andExpect(status().isBadRequest());

        List<BackgroundColor> backgroundColorList = backgroundColorRepository.findAll();
        assertThat(backgroundColorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = backgroundColorRepository.findAll().size();
        // set the field null
        backgroundColor.setCode(null);

        // Create the BackgroundColor, which fails.
        BackgroundColorDTO backgroundColorDTO = backgroundColorMapper.toDto(backgroundColor);


        restBackgroundColorMockMvc.perform(post("/api/background-colors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backgroundColorDTO)))
            .andExpect(status().isBadRequest());

        List<BackgroundColor> backgroundColorList = backgroundColorRepository.findAll();
        assertThat(backgroundColorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBackgroundColors() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList
        restBackgroundColorMockMvc.perform(get("/api/background-colors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backgroundColor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getBackgroundColor() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get the backgroundColor
        restBackgroundColorMockMvc.perform(get("/api/background-colors/{id}", backgroundColor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(backgroundColor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }


    @Test
    @Transactional
    public void getBackgroundColorsByIdFiltering() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        Long id = backgroundColor.getId();

        defaultBackgroundColorShouldBeFound("id.equals=" + id);
        defaultBackgroundColorShouldNotBeFound("id.notEquals=" + id);

        defaultBackgroundColorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBackgroundColorShouldNotBeFound("id.greaterThan=" + id);

        defaultBackgroundColorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBackgroundColorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBackgroundColorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where name equals to DEFAULT_NAME
        defaultBackgroundColorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the backgroundColorList where name equals to UPDATED_NAME
        defaultBackgroundColorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBackgroundColorsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where name not equals to DEFAULT_NAME
        defaultBackgroundColorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the backgroundColorList where name not equals to UPDATED_NAME
        defaultBackgroundColorShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBackgroundColorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBackgroundColorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the backgroundColorList where name equals to UPDATED_NAME
        defaultBackgroundColorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBackgroundColorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where name is not null
        defaultBackgroundColorShouldBeFound("name.specified=true");

        // Get all the backgroundColorList where name is null
        defaultBackgroundColorShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllBackgroundColorsByNameContainsSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where name contains DEFAULT_NAME
        defaultBackgroundColorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the backgroundColorList where name contains UPDATED_NAME
        defaultBackgroundColorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBackgroundColorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where name does not contain DEFAULT_NAME
        defaultBackgroundColorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the backgroundColorList where name does not contain UPDATED_NAME
        defaultBackgroundColorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllBackgroundColorsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where code equals to DEFAULT_CODE
        defaultBackgroundColorShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the backgroundColorList where code equals to UPDATED_CODE
        defaultBackgroundColorShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBackgroundColorsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where code not equals to DEFAULT_CODE
        defaultBackgroundColorShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the backgroundColorList where code not equals to UPDATED_CODE
        defaultBackgroundColorShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBackgroundColorsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where code in DEFAULT_CODE or UPDATED_CODE
        defaultBackgroundColorShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the backgroundColorList where code equals to UPDATED_CODE
        defaultBackgroundColorShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBackgroundColorsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where code is not null
        defaultBackgroundColorShouldBeFound("code.specified=true");

        // Get all the backgroundColorList where code is null
        defaultBackgroundColorShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllBackgroundColorsByCodeContainsSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where code contains DEFAULT_CODE
        defaultBackgroundColorShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the backgroundColorList where code contains UPDATED_CODE
        defaultBackgroundColorShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBackgroundColorsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        // Get all the backgroundColorList where code does not contain DEFAULT_CODE
        defaultBackgroundColorShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the backgroundColorList where code does not contain UPDATED_CODE
        defaultBackgroundColorShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllBackgroundColorsByExtendedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);
        ExtendedUser extendedUser = ExtendedUserResourceIT.createEntity(em);
        em.persist(extendedUser);
        em.flush();
        backgroundColor.addExtendedUser(extendedUser);
        backgroundColorRepository.saveAndFlush(backgroundColor);
        Long extendedUserId = extendedUser.getId();

        // Get all the backgroundColorList where extendedUser equals to extendedUserId
        defaultBackgroundColorShouldBeFound("extendedUserId.equals=" + extendedUserId);

        // Get all the backgroundColorList where extendedUser equals to extendedUserId + 1
        defaultBackgroundColorShouldNotBeFound("extendedUserId.equals=" + (extendedUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBackgroundColorShouldBeFound(String filter) throws Exception {
        restBackgroundColorMockMvc.perform(get("/api/background-colors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backgroundColor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));

        // Check, that the count call also returns 1
        restBackgroundColorMockMvc.perform(get("/api/background-colors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBackgroundColorShouldNotBeFound(String filter) throws Exception {
        restBackgroundColorMockMvc.perform(get("/api/background-colors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBackgroundColorMockMvc.perform(get("/api/background-colors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBackgroundColor() throws Exception {
        // Get the backgroundColor
        restBackgroundColorMockMvc.perform(get("/api/background-colors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBackgroundColor() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        int databaseSizeBeforeUpdate = backgroundColorRepository.findAll().size();

        // Update the backgroundColor
        BackgroundColor updatedBackgroundColor = backgroundColorRepository.findById(backgroundColor.getId()).get();
        // Disconnect from session so that the updates on updatedBackgroundColor are not directly saved in db
        em.detach(updatedBackgroundColor);
        updatedBackgroundColor
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);
        BackgroundColorDTO backgroundColorDTO = backgroundColorMapper.toDto(updatedBackgroundColor);

        restBackgroundColorMockMvc.perform(put("/api/background-colors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backgroundColorDTO)))
            .andExpect(status().isOk());

        // Validate the BackgroundColor in the database
        List<BackgroundColor> backgroundColorList = backgroundColorRepository.findAll();
        assertThat(backgroundColorList).hasSize(databaseSizeBeforeUpdate);
        BackgroundColor testBackgroundColor = backgroundColorList.get(backgroundColorList.size() - 1);
        assertThat(testBackgroundColor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBackgroundColor.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBackgroundColor() throws Exception {
        int databaseSizeBeforeUpdate = backgroundColorRepository.findAll().size();

        // Create the BackgroundColor
        BackgroundColorDTO backgroundColorDTO = backgroundColorMapper.toDto(backgroundColor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBackgroundColorMockMvc.perform(put("/api/background-colors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(backgroundColorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BackgroundColor in the database
        List<BackgroundColor> backgroundColorList = backgroundColorRepository.findAll();
        assertThat(backgroundColorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBackgroundColor() throws Exception {
        // Initialize the database
        backgroundColorRepository.saveAndFlush(backgroundColor);

        int databaseSizeBeforeDelete = backgroundColorRepository.findAll().size();

        // Delete the backgroundColor
        restBackgroundColorMockMvc.perform(delete("/api/background-colors/{id}", backgroundColor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BackgroundColor> backgroundColorList = backgroundColorRepository.findAll();
        assertThat(backgroundColorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
