package com.project.mydraw.web.rest;

import com.project.mydraw.MydrawApp;
import com.project.mydraw.domain.Album;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.domain.Category;
import com.project.mydraw.repository.AlbumRepository;
import com.project.mydraw.service.AlbumService;
import com.project.mydraw.service.dto.AlbumDTO;
import com.project.mydraw.service.mapper.AlbumMapper;
import com.project.mydraw.service.AlbumQueryService;

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
 * Integration tests for the {@link AlbumResource} REST controller.
 */
@SpringBootTest(classes = MydrawApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlbumResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    private static final Integer SMALLER_ORDER = 1 - 1;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumQueryService albumQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlbumMockMvc;

    private Album album;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createEntity(EntityManager em) {
        Album album = new Album()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .order(DEFAULT_ORDER)
            .date(DEFAULT_DATE);
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        album.setExtendedUser(extendedUser);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        album.setCategory(category);
        return album;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createUpdatedEntity(EntityManager em) {
        Album album = new Album()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .order(UPDATED_ORDER)
            .date(UPDATED_DATE);
        // Add required entity
        ExtendedUser extendedUser;
        if (TestUtil.findAll(em, ExtendedUser.class).isEmpty()) {
            extendedUser = ExtendedUserResourceIT.createUpdatedEntity(em);
            em.persist(extendedUser);
            em.flush();
        } else {
            extendedUser = TestUtil.findAll(em, ExtendedUser.class).get(0);
        }
        album.setExtendedUser(extendedUser);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        album.setCategory(category);
        return album;
    }

    @BeforeEach
    public void initTest() {
        album = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbum() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();
        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate + 1);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlbum.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlbum.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAlbum.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testAlbum.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAlbum.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album with an existing ID
        album.setId(1L);
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = albumRepository.findAll().size();
        // set the field null
        album.setName(null);

        // Create the Album, which fails.
        AlbumDTO albumDTO = albumMapper.toDto(album);


        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = albumRepository.findAll().size();
        // set the field null
        album.setImage(null);

        // Create the Album, which fails.
        AlbumDTO albumDTO = albumMapper.toDto(album);


        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = albumRepository.findAll().size();
        // set the field null
        album.setImageContentType(null);

        // Create the Album, which fails.
        AlbumDTO albumDTO = albumMapper.toDto(album);


        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = albumRepository.findAll().size();
        // set the field null
        album.setOrder(null);

        // Create the Album, which fails.
        AlbumDTO albumDTO = albumMapper.toDto(album);


        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = albumRepository.findAll().size();
        // set the field null
        album.setDate(null);

        // Create the Album, which fails.
        AlbumDTO albumDTO = albumMapper.toDto(album);


        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlbums() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", album.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(album.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }


    @Test
    @Transactional
    public void getAlbumsByIdFiltering() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        Long id = album.getId();

        defaultAlbumShouldBeFound("id.equals=" + id);
        defaultAlbumShouldNotBeFound("id.notEquals=" + id);

        defaultAlbumShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlbumShouldNotBeFound("id.greaterThan=" + id);

        defaultAlbumShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlbumShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlbumsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name equals to DEFAULT_NAME
        defaultAlbumShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the albumList where name equals to UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name not equals to DEFAULT_NAME
        defaultAlbumShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the albumList where name not equals to UPDATED_NAME
        defaultAlbumShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAlbumShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the albumList where name equals to UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name is not null
        defaultAlbumShouldBeFound("name.specified=true");

        // Get all the albumList where name is null
        defaultAlbumShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByNameContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name contains DEFAULT_NAME
        defaultAlbumShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the albumList where name contains UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name does not contain DEFAULT_NAME
        defaultAlbumShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the albumList where name does not contain UPDATED_NAME
        defaultAlbumShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAlbumsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where description equals to DEFAULT_DESCRIPTION
        defaultAlbumShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the albumList where description equals to UPDATED_DESCRIPTION
        defaultAlbumShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where description not equals to DEFAULT_DESCRIPTION
        defaultAlbumShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the albumList where description not equals to UPDATED_DESCRIPTION
        defaultAlbumShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAlbumShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the albumList where description equals to UPDATED_DESCRIPTION
        defaultAlbumShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where description is not null
        defaultAlbumShouldBeFound("description.specified=true");

        // Get all the albumList where description is null
        defaultAlbumShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where description contains DEFAULT_DESCRIPTION
        defaultAlbumShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the albumList where description contains UPDATED_DESCRIPTION
        defaultAlbumShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where description does not contain DEFAULT_DESCRIPTION
        defaultAlbumShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the albumList where description does not contain UPDATED_DESCRIPTION
        defaultAlbumShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAlbumsByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where image equals to DEFAULT_IMAGE
        defaultAlbumShouldBeFound("image.equals=" + DEFAULT_IMAGE);

        // Get all the albumList where image equals to UPDATED_IMAGE
        defaultAlbumShouldNotBeFound("image.equals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByImageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where image not equals to DEFAULT_IMAGE
        defaultAlbumShouldNotBeFound("image.notEquals=" + DEFAULT_IMAGE);

        // Get all the albumList where image not equals to UPDATED_IMAGE
        defaultAlbumShouldBeFound("image.notEquals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByImageIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where image in DEFAULT_IMAGE or UPDATED_IMAGE
        defaultAlbumShouldBeFound("image.in=" + DEFAULT_IMAGE + "," + UPDATED_IMAGE);

        // Get all the albumList where image equals to UPDATED_IMAGE
        defaultAlbumShouldNotBeFound("image.in=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where image is not null
        defaultAlbumShouldBeFound("image.specified=true");

        // Get all the albumList where image is null
        defaultAlbumShouldNotBeFound("image.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByImageContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where image contains DEFAULT_IMAGE
        defaultAlbumShouldBeFound("image.contains=" + DEFAULT_IMAGE);

        // Get all the albumList where image contains UPDATED_IMAGE
        defaultAlbumShouldNotBeFound("image.contains=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByImageNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where image does not contain DEFAULT_IMAGE
        defaultAlbumShouldNotBeFound("image.doesNotContain=" + DEFAULT_IMAGE);

        // Get all the albumList where image does not contain UPDATED_IMAGE
        defaultAlbumShouldBeFound("image.doesNotContain=" + UPDATED_IMAGE);
    }


    @Test
    @Transactional
    public void getAllAlbumsByImageContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where imageContentType equals to DEFAULT_IMAGE_CONTENT_TYPE
        defaultAlbumShouldBeFound("imageContentType.equals=" + DEFAULT_IMAGE_CONTENT_TYPE);

        // Get all the albumList where imageContentType equals to UPDATED_IMAGE_CONTENT_TYPE
        defaultAlbumShouldNotBeFound("imageContentType.equals=" + UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByImageContentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where imageContentType not equals to DEFAULT_IMAGE_CONTENT_TYPE
        defaultAlbumShouldNotBeFound("imageContentType.notEquals=" + DEFAULT_IMAGE_CONTENT_TYPE);

        // Get all the albumList where imageContentType not equals to UPDATED_IMAGE_CONTENT_TYPE
        defaultAlbumShouldBeFound("imageContentType.notEquals=" + UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByImageContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where imageContentType in DEFAULT_IMAGE_CONTENT_TYPE or UPDATED_IMAGE_CONTENT_TYPE
        defaultAlbumShouldBeFound("imageContentType.in=" + DEFAULT_IMAGE_CONTENT_TYPE + "," + UPDATED_IMAGE_CONTENT_TYPE);

        // Get all the albumList where imageContentType equals to UPDATED_IMAGE_CONTENT_TYPE
        defaultAlbumShouldNotBeFound("imageContentType.in=" + UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByImageContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where imageContentType is not null
        defaultAlbumShouldBeFound("imageContentType.specified=true");

        // Get all the albumList where imageContentType is null
        defaultAlbumShouldNotBeFound("imageContentType.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByImageContentTypeContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where imageContentType contains DEFAULT_IMAGE_CONTENT_TYPE
        defaultAlbumShouldBeFound("imageContentType.contains=" + DEFAULT_IMAGE_CONTENT_TYPE);

        // Get all the albumList where imageContentType contains UPDATED_IMAGE_CONTENT_TYPE
        defaultAlbumShouldNotBeFound("imageContentType.contains=" + UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByImageContentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where imageContentType does not contain DEFAULT_IMAGE_CONTENT_TYPE
        defaultAlbumShouldNotBeFound("imageContentType.doesNotContain=" + DEFAULT_IMAGE_CONTENT_TYPE);

        // Get all the albumList where imageContentType does not contain UPDATED_IMAGE_CONTENT_TYPE
        defaultAlbumShouldBeFound("imageContentType.doesNotContain=" + UPDATED_IMAGE_CONTENT_TYPE);
    }


    @Test
    @Transactional
    public void getAllAlbumsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where order equals to DEFAULT_ORDER
        defaultAlbumShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the albumList where order equals to UPDATED_ORDER
        defaultAlbumShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllAlbumsByOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where order not equals to DEFAULT_ORDER
        defaultAlbumShouldNotBeFound("order.notEquals=" + DEFAULT_ORDER);

        // Get all the albumList where order not equals to UPDATED_ORDER
        defaultAlbumShouldBeFound("order.notEquals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllAlbumsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultAlbumShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the albumList where order equals to UPDATED_ORDER
        defaultAlbumShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllAlbumsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where order is not null
        defaultAlbumShouldBeFound("order.specified=true");

        // Get all the albumList where order is null
        defaultAlbumShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlbumsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where order is greater than or equal to DEFAULT_ORDER
        defaultAlbumShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the albumList where order is greater than or equal to UPDATED_ORDER
        defaultAlbumShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllAlbumsByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where order is less than or equal to DEFAULT_ORDER
        defaultAlbumShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the albumList where order is less than or equal to SMALLER_ORDER
        defaultAlbumShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    public void getAllAlbumsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where order is less than DEFAULT_ORDER
        defaultAlbumShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the albumList where order is less than UPDATED_ORDER
        defaultAlbumShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllAlbumsByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where order is greater than DEFAULT_ORDER
        defaultAlbumShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the albumList where order is greater than SMALLER_ORDER
        defaultAlbumShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }


    @Test
    @Transactional
    public void getAllAlbumsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where date equals to DEFAULT_DATE
        defaultAlbumShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the albumList where date equals to UPDATED_DATE
        defaultAlbumShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where date not equals to DEFAULT_DATE
        defaultAlbumShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the albumList where date not equals to UPDATED_DATE
        defaultAlbumShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where date in DEFAULT_DATE or UPDATED_DATE
        defaultAlbumShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the albumList where date equals to UPDATED_DATE
        defaultAlbumShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllAlbumsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where date is not null
        defaultAlbumShouldBeFound("date.specified=true");

        // Get all the albumList where date is null
        defaultAlbumShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlbumsByExtendedUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        ExtendedUser extendedUser = album.getExtendedUser();
        albumRepository.saveAndFlush(album);
        Long extendedUserId = extendedUser.getId();

        // Get all the albumList where extendedUser equals to extendedUserId
        defaultAlbumShouldBeFound("extendedUserId.equals=" + extendedUserId);

        // Get all the albumList where extendedUser equals to extendedUserId + 1
        defaultAlbumShouldNotBeFound("extendedUserId.equals=" + (extendedUserId + 1));
    }


    @Test
    @Transactional
    public void getAllAlbumsByCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category category = album.getCategory();
        albumRepository.saveAndFlush(album);
        Long categoryId = category.getId();

        // Get all the albumList where category equals to categoryId
        defaultAlbumShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the albumList where category equals to categoryId + 1
        defaultAlbumShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlbumShouldBeFound(String filter) throws Exception {
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restAlbumMockMvc.perform(get("/api/albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlbumShouldNotBeFound(String filter) throws Exception {
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlbumMockMvc.perform(get("/api/albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlbum() throws Exception {
        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Update the album
        Album updatedAlbum = albumRepository.findById(album.getId()).get();
        // Disconnect from session so that the updates on updatedAlbum are not directly saved in db
        em.detach(updatedAlbum);
        updatedAlbum
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .order(UPDATED_ORDER)
            .date(UPDATED_DATE);
        AlbumDTO albumDTO = albumMapper.toDto(updatedAlbum);

        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isOk());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlbum.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlbum.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAlbum.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testAlbum.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAlbum.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbum() throws Exception {
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeDelete = albumRepository.findAll().size();

        // Delete the album
        restAlbumMockMvc.perform(delete("/api/albums/{id}", album.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
