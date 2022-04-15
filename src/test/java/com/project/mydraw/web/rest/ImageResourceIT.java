package com.project.mydraw.web.rest;

import com.project.mydraw.MydrawApp;
import com.project.mydraw.domain.Image;
import com.project.mydraw.domain.Album;
import com.project.mydraw.repository.ImageRepository;
import com.project.mydraw.service.ImageService;
import com.project.mydraw.service.dto.ImageDTO;
import com.project.mydraw.service.mapper.ImageMapper;
import com.project.mydraw.service.dto.ImageCriteria;
import com.project.mydraw.service.ImageQueryService;

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
 * Integration tests for the {@link ImageResource} REST controller.
 */
@SpringBootTest(classes = MydrawApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ImageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "BBBBBBBBBB";

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageQueryService imageQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImageMockMvc;

    private Image image;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Image createEntity(EntityManager em) {
        Image image = new Image()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        // Add required entity
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            album = AlbumResourceIT.createEntity(em);
            em.persist(album);
            em.flush();
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        image.setAlbum(album);
        return image;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Image createUpdatedEntity(EntityManager em) {
        Image image = new Image()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        // Add required entity
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            album = AlbumResourceIT.createUpdatedEntity(em);
            em.persist(album);
            em.flush();
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        image.setAlbum(album);
        return image;
    }

    @BeforeEach
    public void initTest() {
        image = createEntity(em);
    }

    @Test
    @Transactional
    public void createImage() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();
        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);
        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate + 1);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testImage.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testImage.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image with an existing ID
        image.setId(1L);
        ImageDTO imageDTO = imageMapper.toDto(image);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setName(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);


        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setImage(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);


        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageContentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setImageContentType(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);


        restImageMockMvc.perform(post("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList
        restImageMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(image.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE));
    }


    @Test
    @Transactional
    public void getImagesByIdFiltering() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        Long id = image.getId();

        defaultImageShouldBeFound("id.equals=" + id);
        defaultImageShouldNotBeFound("id.notEquals=" + id);

        defaultImageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImageShouldNotBeFound("id.greaterThan=" + id);

        defaultImageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImageShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllImagesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where name equals to DEFAULT_NAME
        defaultImageShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the imageList where name equals to UPDATED_NAME
        defaultImageShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllImagesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where name not equals to DEFAULT_NAME
        defaultImageShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the imageList where name not equals to UPDATED_NAME
        defaultImageShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllImagesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where name in DEFAULT_NAME or UPDATED_NAME
        defaultImageShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the imageList where name equals to UPDATED_NAME
        defaultImageShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllImagesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where name is not null
        defaultImageShouldBeFound("name.specified=true");

        // Get all the imageList where name is null
        defaultImageShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllImagesByNameContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where name contains DEFAULT_NAME
        defaultImageShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the imageList where name contains UPDATED_NAME
        defaultImageShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllImagesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where name does not contain DEFAULT_NAME
        defaultImageShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the imageList where name does not contain UPDATED_NAME
        defaultImageShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllImagesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where description equals to DEFAULT_DESCRIPTION
        defaultImageShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the imageList where description equals to UPDATED_DESCRIPTION
        defaultImageShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImagesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where description not equals to DEFAULT_DESCRIPTION
        defaultImageShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the imageList where description not equals to UPDATED_DESCRIPTION
        defaultImageShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImagesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultImageShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the imageList where description equals to UPDATED_DESCRIPTION
        defaultImageShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImagesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where description is not null
        defaultImageShouldBeFound("description.specified=true");

        // Get all the imageList where description is null
        defaultImageShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllImagesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where description contains DEFAULT_DESCRIPTION
        defaultImageShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the imageList where description contains UPDATED_DESCRIPTION
        defaultImageShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImagesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where description does not contain DEFAULT_DESCRIPTION
        defaultImageShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the imageList where description does not contain UPDATED_DESCRIPTION
        defaultImageShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllImagesByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where image equals to DEFAULT_IMAGE
        defaultImageShouldBeFound("image.equals=" + DEFAULT_IMAGE);

        // Get all the imageList where image equals to UPDATED_IMAGE
        defaultImageShouldNotBeFound("image.equals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllImagesByImageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where image not equals to DEFAULT_IMAGE
        defaultImageShouldNotBeFound("image.notEquals=" + DEFAULT_IMAGE);

        // Get all the imageList where image not equals to UPDATED_IMAGE
        defaultImageShouldBeFound("image.notEquals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllImagesByImageIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where image in DEFAULT_IMAGE or UPDATED_IMAGE
        defaultImageShouldBeFound("image.in=" + DEFAULT_IMAGE + "," + UPDATED_IMAGE);

        // Get all the imageList where image equals to UPDATED_IMAGE
        defaultImageShouldNotBeFound("image.in=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllImagesByImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where image is not null
        defaultImageShouldBeFound("image.specified=true");

        // Get all the imageList where image is null
        defaultImageShouldNotBeFound("image.specified=false");
    }
                @Test
    @Transactional
    public void getAllImagesByImageContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where image contains DEFAULT_IMAGE
        defaultImageShouldBeFound("image.contains=" + DEFAULT_IMAGE);

        // Get all the imageList where image contains UPDATED_IMAGE
        defaultImageShouldNotBeFound("image.contains=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllImagesByImageNotContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where image does not contain DEFAULT_IMAGE
        defaultImageShouldNotBeFound("image.doesNotContain=" + DEFAULT_IMAGE);

        // Get all the imageList where image does not contain UPDATED_IMAGE
        defaultImageShouldBeFound("image.doesNotContain=" + UPDATED_IMAGE);
    }


    @Test
    @Transactional
    public void getAllImagesByImageContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imageContentType equals to DEFAULT_IMAGE_CONTENT_TYPE
        defaultImageShouldBeFound("imageContentType.equals=" + DEFAULT_IMAGE_CONTENT_TYPE);

        // Get all the imageList where imageContentType equals to UPDATED_IMAGE_CONTENT_TYPE
        defaultImageShouldNotBeFound("imageContentType.equals=" + UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllImagesByImageContentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imageContentType not equals to DEFAULT_IMAGE_CONTENT_TYPE
        defaultImageShouldNotBeFound("imageContentType.notEquals=" + DEFAULT_IMAGE_CONTENT_TYPE);

        // Get all the imageList where imageContentType not equals to UPDATED_IMAGE_CONTENT_TYPE
        defaultImageShouldBeFound("imageContentType.notEquals=" + UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllImagesByImageContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imageContentType in DEFAULT_IMAGE_CONTENT_TYPE or UPDATED_IMAGE_CONTENT_TYPE
        defaultImageShouldBeFound("imageContentType.in=" + DEFAULT_IMAGE_CONTENT_TYPE + "," + UPDATED_IMAGE_CONTENT_TYPE);

        // Get all the imageList where imageContentType equals to UPDATED_IMAGE_CONTENT_TYPE
        defaultImageShouldNotBeFound("imageContentType.in=" + UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllImagesByImageContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imageContentType is not null
        defaultImageShouldBeFound("imageContentType.specified=true");

        // Get all the imageList where imageContentType is null
        defaultImageShouldNotBeFound("imageContentType.specified=false");
    }
                @Test
    @Transactional
    public void getAllImagesByImageContentTypeContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imageContentType contains DEFAULT_IMAGE_CONTENT_TYPE
        defaultImageShouldBeFound("imageContentType.contains=" + DEFAULT_IMAGE_CONTENT_TYPE);

        // Get all the imageList where imageContentType contains UPDATED_IMAGE_CONTENT_TYPE
        defaultImageShouldNotBeFound("imageContentType.contains=" + UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllImagesByImageContentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList where imageContentType does not contain DEFAULT_IMAGE_CONTENT_TYPE
        defaultImageShouldNotBeFound("imageContentType.doesNotContain=" + DEFAULT_IMAGE_CONTENT_TYPE);

        // Get all the imageList where imageContentType does not contain UPDATED_IMAGE_CONTENT_TYPE
        defaultImageShouldBeFound("imageContentType.doesNotContain=" + UPDATED_IMAGE_CONTENT_TYPE);
    }


    @Test
    @Transactional
    public void getAllImagesByAlbumIsEqualToSomething() throws Exception {
        // Get already existing entity
        Album album = image.getAlbum();
        imageRepository.saveAndFlush(image);
        Long albumId = album.getId();

        // Get all the imageList where album equals to albumId
        defaultImageShouldBeFound("albumId.equals=" + albumId);

        // Get all the imageList where album equals to albumId + 1
        defaultImageShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImageShouldBeFound(String filter) throws Exception {
        restImageMockMvc.perform(get("/api/images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)));

        // Check, that the count call also returns 1
        restImageMockMvc.perform(get("/api/images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImageShouldNotBeFound(String filter) throws Exception {
        restImageMockMvc.perform(get("/api/images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImageMockMvc.perform(get("/api/images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingImage() throws Exception {
        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image
        Image updatedImage = imageRepository.findById(image.getId()).get();
        // Disconnect from session so that the updates on updatedImage are not directly saved in db
        em.detach(updatedImage);
        updatedImage
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ImageDTO imageDTO = imageMapper.toDto(updatedImage);

        restImageMockMvc.perform(put("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testImage.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testImage.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageMockMvc.perform(put("/api/images")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeDelete = imageRepository.findAll().size();

        // Delete the image
        restImageMockMvc.perform(delete("/api/images/{id}", image.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
