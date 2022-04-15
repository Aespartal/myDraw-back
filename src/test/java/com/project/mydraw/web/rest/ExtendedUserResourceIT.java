package com.project.mydraw.web.rest;

import com.project.mydraw.MydrawApp;
import com.project.mydraw.domain.ExtendedUser;
import com.project.mydraw.domain.User;
import com.project.mydraw.domain.Album;
import com.project.mydraw.domain.BackgroundColor;
import com.project.mydraw.repository.ExtendedUserRepository;
import com.project.mydraw.service.ExtendedUserService;
import com.project.mydraw.service.dto.ExtendedUserDTO;
import com.project.mydraw.service.mapper.ExtendedUserMapper;
import com.project.mydraw.service.dto.ExtendedUserCriteria;
import com.project.mydraw.service.ExtendedUserQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.project.mydraw.domain.enumeration.Gender;
/**
 * Integration tests for the {@link ExtendedUserResource} REST controller.
 */
@SpringBootTest(classes = MydrawApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExtendedUserResourceIT {

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTHDATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_COVER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_COVER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_COVER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_COVER_CONTENT_TYPE = "image/png";

    private static final Gender DEFAULT_GENDER = Gender.MEN;
    private static final Gender UPDATED_GENDER = Gender.WOMAN;

    @Autowired
    private ExtendedUserRepository extendedUserRepository;

    @Autowired
    private ExtendedUserMapper extendedUserMapper;

    @Autowired
    private ExtendedUserService extendedUserService;

    @Autowired
    private ExtendedUserQueryService extendedUserQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExtendedUserMockMvc;

    private ExtendedUser extendedUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtendedUser createEntity(EntityManager em) {
        ExtendedUser extendedUser = new ExtendedUser()
            .telephone(DEFAULT_TELEPHONE)
            .birthdate(DEFAULT_BIRTHDATE)
            .description(DEFAULT_DESCRIPTION)
            .imageCover(DEFAULT_IMAGE_COVER)
            .imageCoverContentType(DEFAULT_IMAGE_COVER_CONTENT_TYPE)
            .gender(DEFAULT_GENDER);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        extendedUser.setUser(user);
        // Add required entity
        BackgroundColor backgroundColor;
        if (TestUtil.findAll(em, BackgroundColor.class).isEmpty()) {
            backgroundColor = BackgroundColorResourceIT.createEntity(em);
            em.persist(backgroundColor);
            em.flush();
        } else {
            backgroundColor = TestUtil.findAll(em, BackgroundColor.class).get(0);
        }
        extendedUser.setBackgroundColor(backgroundColor);
        return extendedUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtendedUser createUpdatedEntity(EntityManager em) {
        ExtendedUser extendedUser = new ExtendedUser()
            .telephone(UPDATED_TELEPHONE)
            .birthdate(UPDATED_BIRTHDATE)
            .description(UPDATED_DESCRIPTION)
            .imageCover(UPDATED_IMAGE_COVER)
            .imageCoverContentType(UPDATED_IMAGE_COVER_CONTENT_TYPE)
            .gender(UPDATED_GENDER);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        extendedUser.setUser(user);
        // Add required entity
        BackgroundColor backgroundColor;
        if (TestUtil.findAll(em, BackgroundColor.class).isEmpty()) {
            backgroundColor = BackgroundColorResourceIT.createUpdatedEntity(em);
            em.persist(backgroundColor);
            em.flush();
        } else {
            backgroundColor = TestUtil.findAll(em, BackgroundColor.class).get(0);
        }
        extendedUser.setBackgroundColor(backgroundColor);
        return extendedUser;
    }

    @BeforeEach
    public void initTest() {
        extendedUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtendedUser() throws Exception {
        int databaseSizeBeforeCreate = extendedUserRepository.findAll().size();
        // Create the ExtendedUser
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);
        restExtendedUserMockMvc.perform(post("/api/extended-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeCreate + 1);
        ExtendedUser testExtendedUser = extendedUserList.get(extendedUserList.size() - 1);
        assertThat(testExtendedUser.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testExtendedUser.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testExtendedUser.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testExtendedUser.getImageCover()).isEqualTo(DEFAULT_IMAGE_COVER);
        assertThat(testExtendedUser.getImageCoverContentType()).isEqualTo(DEFAULT_IMAGE_COVER_CONTENT_TYPE);
        assertThat(testExtendedUser.getGender()).isEqualTo(DEFAULT_GENDER);

        // Validate the id for MapsId, the ids must be same
        assertThat(testExtendedUser.getId()).isEqualTo(testExtendedUser.getUser().getId());
    }

    @Test
    @Transactional
    public void createExtendedUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser with an existing ID
        extendedUser.setId(1L);
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtendedUserMockMvc.perform(post("/api/extended-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateExtendedUserMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);
        int databaseSizeBeforeCreate = extendedUserRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the extendedUser
        ExtendedUser updatedExtendedUser = extendedUserRepository.findById(extendedUser.getId()).get();
        // Disconnect from session so that the updates on updatedExtendedUser are not directly saved in db
        em.detach(updatedExtendedUser);

        // Update the User with new association value
        updatedExtendedUser.setUser(user);
        ExtendedUserDTO updatedExtendedUserDTO = extendedUserMapper.toDto(updatedExtendedUser);

        // Update the entity
        restExtendedUserMockMvc.perform(put("/api/extended-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExtendedUserDTO)))
            .andExpect(status().isOk());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeCreate);
        ExtendedUser testExtendedUser = extendedUserList.get(extendedUserList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testExtendedUser.getId()).isEqualTo(testExtendedUser.getUser().getId());
    }

    @Test
    @Transactional
    public void checkBirthdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = extendedUserRepository.findAll().size();
        // set the field null
        extendedUser.setBirthdate(null);

        // Create the ExtendedUser, which fails.
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);


        restExtendedUserMockMvc.perform(post("/api/extended-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isBadRequest());

        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = extendedUserRepository.findAll().size();
        // set the field null
        extendedUser.setGender(null);

        // Create the ExtendedUser, which fails.
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);


        restExtendedUserMockMvc.perform(post("/api/extended-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isBadRequest());

        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExtendedUsers() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList
        restExtendedUserMockMvc.perform(get("/api/extended-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extendedUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageCoverContentType").value(hasItem(DEFAULT_IMAGE_COVER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageCover").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_COVER))))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }
    
    @Test
    @Transactional
    public void getExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get the extendedUser
        restExtendedUserMockMvc.perform(get("/api/extended-users/{id}", extendedUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(extendedUser.getId().intValue()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageCoverContentType").value(DEFAULT_IMAGE_COVER_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageCover").value(Base64Utils.encodeToString(DEFAULT_IMAGE_COVER)))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }


    @Test
    @Transactional
    public void getExtendedUsersByIdFiltering() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        Long id = extendedUser.getId();

        defaultExtendedUserShouldBeFound("id.equals=" + id);
        defaultExtendedUserShouldNotBeFound("id.notEquals=" + id);

        defaultExtendedUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExtendedUserShouldNotBeFound("id.greaterThan=" + id);

        defaultExtendedUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExtendedUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExtendedUsersByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where telephone equals to DEFAULT_TELEPHONE
        defaultExtendedUserShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the extendedUserList where telephone equals to UPDATED_TELEPHONE
        defaultExtendedUserShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where telephone not equals to DEFAULT_TELEPHONE
        defaultExtendedUserShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the extendedUserList where telephone not equals to UPDATED_TELEPHONE
        defaultExtendedUserShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultExtendedUserShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the extendedUserList where telephone equals to UPDATED_TELEPHONE
        defaultExtendedUserShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where telephone is not null
        defaultExtendedUserShouldBeFound("telephone.specified=true");

        // Get all the extendedUserList where telephone is null
        defaultExtendedUserShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllExtendedUsersByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where telephone contains DEFAULT_TELEPHONE
        defaultExtendedUserShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the extendedUserList where telephone contains UPDATED_TELEPHONE
        defaultExtendedUserShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where telephone does not contain DEFAULT_TELEPHONE
        defaultExtendedUserShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the extendedUserList where telephone does not contain UPDATED_TELEPHONE
        defaultExtendedUserShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllExtendedUsersByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where birthdate equals to DEFAULT_BIRTHDATE
        defaultExtendedUserShouldBeFound("birthdate.equals=" + DEFAULT_BIRTHDATE);

        // Get all the extendedUserList where birthdate equals to UPDATED_BIRTHDATE
        defaultExtendedUserShouldNotBeFound("birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByBirthdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where birthdate not equals to DEFAULT_BIRTHDATE
        defaultExtendedUserShouldNotBeFound("birthdate.notEquals=" + DEFAULT_BIRTHDATE);

        // Get all the extendedUserList where birthdate not equals to UPDATED_BIRTHDATE
        defaultExtendedUserShouldBeFound("birthdate.notEquals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where birthdate in DEFAULT_BIRTHDATE or UPDATED_BIRTHDATE
        defaultExtendedUserShouldBeFound("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE);

        // Get all the extendedUserList where birthdate equals to UPDATED_BIRTHDATE
        defaultExtendedUserShouldNotBeFound("birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where birthdate is not null
        defaultExtendedUserShouldBeFound("birthdate.specified=true");

        // Get all the extendedUserList where birthdate is null
        defaultExtendedUserShouldNotBeFound("birthdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByBirthdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where birthdate is greater than or equal to DEFAULT_BIRTHDATE
        defaultExtendedUserShouldBeFound("birthdate.greaterThanOrEqual=" + DEFAULT_BIRTHDATE);

        // Get all the extendedUserList where birthdate is greater than or equal to UPDATED_BIRTHDATE
        defaultExtendedUserShouldNotBeFound("birthdate.greaterThanOrEqual=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByBirthdateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where birthdate is less than or equal to DEFAULT_BIRTHDATE
        defaultExtendedUserShouldBeFound("birthdate.lessThanOrEqual=" + DEFAULT_BIRTHDATE);

        // Get all the extendedUserList where birthdate is less than or equal to SMALLER_BIRTHDATE
        defaultExtendedUserShouldNotBeFound("birthdate.lessThanOrEqual=" + SMALLER_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByBirthdateIsLessThanSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where birthdate is less than DEFAULT_BIRTHDATE
        defaultExtendedUserShouldNotBeFound("birthdate.lessThan=" + DEFAULT_BIRTHDATE);

        // Get all the extendedUserList where birthdate is less than UPDATED_BIRTHDATE
        defaultExtendedUserShouldBeFound("birthdate.lessThan=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByBirthdateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where birthdate is greater than DEFAULT_BIRTHDATE
        defaultExtendedUserShouldNotBeFound("birthdate.greaterThan=" + DEFAULT_BIRTHDATE);

        // Get all the extendedUserList where birthdate is greater than SMALLER_BIRTHDATE
        defaultExtendedUserShouldBeFound("birthdate.greaterThan=" + SMALLER_BIRTHDATE);
    }


    @Test
    @Transactional
    public void getAllExtendedUsersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where description equals to DEFAULT_DESCRIPTION
        defaultExtendedUserShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the extendedUserList where description equals to UPDATED_DESCRIPTION
        defaultExtendedUserShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where description not equals to DEFAULT_DESCRIPTION
        defaultExtendedUserShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the extendedUserList where description not equals to UPDATED_DESCRIPTION
        defaultExtendedUserShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultExtendedUserShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the extendedUserList where description equals to UPDATED_DESCRIPTION
        defaultExtendedUserShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where description is not null
        defaultExtendedUserShouldBeFound("description.specified=true");

        // Get all the extendedUserList where description is null
        defaultExtendedUserShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllExtendedUsersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where description contains DEFAULT_DESCRIPTION
        defaultExtendedUserShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the extendedUserList where description contains UPDATED_DESCRIPTION
        defaultExtendedUserShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where description does not contain DEFAULT_DESCRIPTION
        defaultExtendedUserShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the extendedUserList where description does not contain UPDATED_DESCRIPTION
        defaultExtendedUserShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllExtendedUsersByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where gender equals to DEFAULT_GENDER
        defaultExtendedUserShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the extendedUserList where gender equals to UPDATED_GENDER
        defaultExtendedUserShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where gender not equals to DEFAULT_GENDER
        defaultExtendedUserShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the extendedUserList where gender not equals to UPDATED_GENDER
        defaultExtendedUserShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultExtendedUserShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the extendedUserList where gender equals to UPDATED_GENDER
        defaultExtendedUserShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        // Get all the extendedUserList where gender is not null
        defaultExtendedUserShouldBeFound("gender.specified=true");

        // Get all the extendedUserList where gender is null
        defaultExtendedUserShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllExtendedUsersByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = extendedUser.getUser();
        extendedUserRepository.saveAndFlush(extendedUser);
        Long userId = user.getId();

        // Get all the extendedUserList where user equals to userId
        defaultExtendedUserShouldBeFound("userId.equals=" + userId);

        // Get all the extendedUserList where user equals to userId + 1
        defaultExtendedUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllExtendedUsersByAlbumIsEqualToSomething() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);
        Album album = AlbumResourceIT.createEntity(em);
        em.persist(album);
        em.flush();
        extendedUser.addAlbum(album);
        extendedUserRepository.saveAndFlush(extendedUser);
        Long albumId = album.getId();

        // Get all the extendedUserList where album equals to albumId
        defaultExtendedUserShouldBeFound("albumId.equals=" + albumId);

        // Get all the extendedUserList where album equals to albumId + 1
        defaultExtendedUserShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }


    @Test
    @Transactional
    public void getAllExtendedUsersByBackgroundColorIsEqualToSomething() throws Exception {
        // Get already existing entity
        BackgroundColor backgroundColor = extendedUser.getBackgroundColor();
        extendedUserRepository.saveAndFlush(extendedUser);
        Long backgroundColorId = backgroundColor.getId();

        // Get all the extendedUserList where backgroundColor equals to backgroundColorId
        defaultExtendedUserShouldBeFound("backgroundColorId.equals=" + backgroundColorId);

        // Get all the extendedUserList where backgroundColor equals to backgroundColorId + 1
        defaultExtendedUserShouldNotBeFound("backgroundColorId.equals=" + (backgroundColorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExtendedUserShouldBeFound(String filter) throws Exception {
        restExtendedUserMockMvc.perform(get("/api/extended-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extendedUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageCoverContentType").value(hasItem(DEFAULT_IMAGE_COVER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageCover").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_COVER))))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));

        // Check, that the count call also returns 1
        restExtendedUserMockMvc.perform(get("/api/extended-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExtendedUserShouldNotBeFound(String filter) throws Exception {
        restExtendedUserMockMvc.perform(get("/api/extended-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExtendedUserMockMvc.perform(get("/api/extended-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExtendedUser() throws Exception {
        // Get the extendedUser
        restExtendedUserMockMvc.perform(get("/api/extended-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        int databaseSizeBeforeUpdate = extendedUserRepository.findAll().size();

        // Update the extendedUser
        ExtendedUser updatedExtendedUser = extendedUserRepository.findById(extendedUser.getId()).get();
        // Disconnect from session so that the updates on updatedExtendedUser are not directly saved in db
        em.detach(updatedExtendedUser);
        updatedExtendedUser
            .telephone(UPDATED_TELEPHONE)
            .birthdate(UPDATED_BIRTHDATE)
            .description(UPDATED_DESCRIPTION)
            .imageCover(UPDATED_IMAGE_COVER)
            .imageCoverContentType(UPDATED_IMAGE_COVER_CONTENT_TYPE)
            .gender(UPDATED_GENDER);
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(updatedExtendedUser);

        restExtendedUserMockMvc.perform(put("/api/extended-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isOk());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeUpdate);
        ExtendedUser testExtendedUser = extendedUserList.get(extendedUserList.size() - 1);
        assertThat(testExtendedUser.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testExtendedUser.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testExtendedUser.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExtendedUser.getImageCover()).isEqualTo(UPDATED_IMAGE_COVER);
        assertThat(testExtendedUser.getImageCoverContentType()).isEqualTo(UPDATED_IMAGE_COVER_CONTENT_TYPE);
        assertThat(testExtendedUser.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void updateNonExistingExtendedUser() throws Exception {
        int databaseSizeBeforeUpdate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtendedUserMockMvc.perform(put("/api/extended-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.saveAndFlush(extendedUser);

        int databaseSizeBeforeDelete = extendedUserRepository.findAll().size();

        // Delete the extendedUser
        restExtendedUserMockMvc.perform(delete("/api/extended-users/{id}", extendedUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
