package com.jhipster.booklib.web.rest;

import com.jhipster.booklib.BooklibApp;
import com.jhipster.booklib.domain.PageUrl;
import com.jhipster.booklib.repository.PageUrlRepository;
import com.jhipster.booklib.service.PageUrlService;
import com.jhipster.booklib.service.dto.PageUrlDTO;
import com.jhipster.booklib.service.mapper.PageUrlMapper;
import com.jhipster.booklib.service.dto.PageUrlCriteria;
import com.jhipster.booklib.service.PageUrlQueryService;

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
 * Integration tests for the {@link PageUrlResource} REST controller.
 */
@SpringBootTest(classes = BooklibApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PageUrlResourceIT {

    private static final String DEFAULT_ISBN = "AAAAAAAAAA";
    private static final String UPDATED_ISBN = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;
    private static final Integer SMALLER_PAGE = 1 - 1;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PageUrlRepository pageUrlRepository;

    @Autowired
    private PageUrlMapper pageUrlMapper;

    @Autowired
    private PageUrlService pageUrlService;

    @Autowired
    private PageUrlQueryService pageUrlQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageUrlMockMvc;

    private PageUrl pageUrl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageUrl createEntity(EntityManager em) {
        PageUrl pageUrl = new PageUrl()
            .isbn(DEFAULT_ISBN)
            .page(DEFAULT_PAGE)
            .hash(DEFAULT_HASH)
            .startTime(DEFAULT_START_TIME);
        return pageUrl;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageUrl createUpdatedEntity(EntityManager em) {
        PageUrl pageUrl = new PageUrl()
            .isbn(UPDATED_ISBN)
            .page(UPDATED_PAGE)
            .hash(UPDATED_HASH)
            .startTime(UPDATED_START_TIME);
        return pageUrl;
    }

    @BeforeEach
    public void initTest() {
        pageUrl = createEntity(em);
    }

    @Test
    @Transactional
    public void createPageUrl() throws Exception {
        int databaseSizeBeforeCreate = pageUrlRepository.findAll().size();

        // Create the PageUrl
        PageUrlDTO pageUrlDTO = pageUrlMapper.toDto(pageUrl);
        restPageUrlMockMvc.perform(post("/api/page-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pageUrlDTO)))
            .andExpect(status().isCreated());

        // Validate the PageUrl in the database
        List<PageUrl> pageUrlList = pageUrlRepository.findAll();
        assertThat(pageUrlList).hasSize(databaseSizeBeforeCreate + 1);
        PageUrl testPageUrl = pageUrlList.get(pageUrlList.size() - 1);
        assertThat(testPageUrl.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testPageUrl.getPage()).isEqualTo(DEFAULT_PAGE);
        assertThat(testPageUrl.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testPageUrl.getStartTime()).isEqualTo(DEFAULT_START_TIME);
    }

    @Test
    @Transactional
    public void createPageUrlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pageUrlRepository.findAll().size();

        // Create the PageUrl with an existing ID
        pageUrl.setId(1L);
        PageUrlDTO pageUrlDTO = pageUrlMapper.toDto(pageUrl);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageUrlMockMvc.perform(post("/api/page-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pageUrlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageUrl in the database
        List<PageUrl> pageUrlList = pageUrlRepository.findAll();
        assertThat(pageUrlList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPageUrls() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList
        restPageUrlMockMvc.perform(get("/api/page-urls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageUrl.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPageUrl() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get the pageUrl
        restPageUrlMockMvc.perform(get("/api/page-urls/{id}", pageUrl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageUrl.getId().intValue()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()));
    }


    @Test
    @Transactional
    public void getPageUrlsByIdFiltering() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        Long id = pageUrl.getId();

        defaultPageUrlShouldBeFound("id.equals=" + id);
        defaultPageUrlShouldNotBeFound("id.notEquals=" + id);

        defaultPageUrlShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPageUrlShouldNotBeFound("id.greaterThan=" + id);

        defaultPageUrlShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPageUrlShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPageUrlsByIsbnIsEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where isbn equals to DEFAULT_ISBN
        defaultPageUrlShouldBeFound("isbn.equals=" + DEFAULT_ISBN);

        // Get all the pageUrlList where isbn equals to UPDATED_ISBN
        defaultPageUrlShouldNotBeFound("isbn.equals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByIsbnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where isbn not equals to DEFAULT_ISBN
        defaultPageUrlShouldNotBeFound("isbn.notEquals=" + DEFAULT_ISBN);

        // Get all the pageUrlList where isbn not equals to UPDATED_ISBN
        defaultPageUrlShouldBeFound("isbn.notEquals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByIsbnIsInShouldWork() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where isbn in DEFAULT_ISBN or UPDATED_ISBN
        defaultPageUrlShouldBeFound("isbn.in=" + DEFAULT_ISBN + "," + UPDATED_ISBN);

        // Get all the pageUrlList where isbn equals to UPDATED_ISBN
        defaultPageUrlShouldNotBeFound("isbn.in=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByIsbnIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where isbn is not null
        defaultPageUrlShouldBeFound("isbn.specified=true");

        // Get all the pageUrlList where isbn is null
        defaultPageUrlShouldNotBeFound("isbn.specified=false");
    }
                @Test
    @Transactional
    public void getAllPageUrlsByIsbnContainsSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where isbn contains DEFAULT_ISBN
        defaultPageUrlShouldBeFound("isbn.contains=" + DEFAULT_ISBN);

        // Get all the pageUrlList where isbn contains UPDATED_ISBN
        defaultPageUrlShouldNotBeFound("isbn.contains=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByIsbnNotContainsSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where isbn does not contain DEFAULT_ISBN
        defaultPageUrlShouldNotBeFound("isbn.doesNotContain=" + DEFAULT_ISBN);

        // Get all the pageUrlList where isbn does not contain UPDATED_ISBN
        defaultPageUrlShouldBeFound("isbn.doesNotContain=" + UPDATED_ISBN);
    }


    @Test
    @Transactional
    public void getAllPageUrlsByPageIsEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where page equals to DEFAULT_PAGE
        defaultPageUrlShouldBeFound("page.equals=" + DEFAULT_PAGE);

        // Get all the pageUrlList where page equals to UPDATED_PAGE
        defaultPageUrlShouldNotBeFound("page.equals=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByPageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where page not equals to DEFAULT_PAGE
        defaultPageUrlShouldNotBeFound("page.notEquals=" + DEFAULT_PAGE);

        // Get all the pageUrlList where page not equals to UPDATED_PAGE
        defaultPageUrlShouldBeFound("page.notEquals=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByPageIsInShouldWork() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where page in DEFAULT_PAGE or UPDATED_PAGE
        defaultPageUrlShouldBeFound("page.in=" + DEFAULT_PAGE + "," + UPDATED_PAGE);

        // Get all the pageUrlList where page equals to UPDATED_PAGE
        defaultPageUrlShouldNotBeFound("page.in=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByPageIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where page is not null
        defaultPageUrlShouldBeFound("page.specified=true");

        // Get all the pageUrlList where page is null
        defaultPageUrlShouldNotBeFound("page.specified=false");
    }

    @Test
    @Transactional
    public void getAllPageUrlsByPageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where page is greater than or equal to DEFAULT_PAGE
        defaultPageUrlShouldBeFound("page.greaterThanOrEqual=" + DEFAULT_PAGE);

        // Get all the pageUrlList where page is greater than or equal to UPDATED_PAGE
        defaultPageUrlShouldNotBeFound("page.greaterThanOrEqual=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByPageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where page is less than or equal to DEFAULT_PAGE
        defaultPageUrlShouldBeFound("page.lessThanOrEqual=" + DEFAULT_PAGE);

        // Get all the pageUrlList where page is less than or equal to SMALLER_PAGE
        defaultPageUrlShouldNotBeFound("page.lessThanOrEqual=" + SMALLER_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByPageIsLessThanSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where page is less than DEFAULT_PAGE
        defaultPageUrlShouldNotBeFound("page.lessThan=" + DEFAULT_PAGE);

        // Get all the pageUrlList where page is less than UPDATED_PAGE
        defaultPageUrlShouldBeFound("page.lessThan=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByPageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where page is greater than DEFAULT_PAGE
        defaultPageUrlShouldNotBeFound("page.greaterThan=" + DEFAULT_PAGE);

        // Get all the pageUrlList where page is greater than SMALLER_PAGE
        defaultPageUrlShouldBeFound("page.greaterThan=" + SMALLER_PAGE);
    }


    @Test
    @Transactional
    public void getAllPageUrlsByHashIsEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where hash equals to DEFAULT_HASH
        defaultPageUrlShouldBeFound("hash.equals=" + DEFAULT_HASH);

        // Get all the pageUrlList where hash equals to UPDATED_HASH
        defaultPageUrlShouldNotBeFound("hash.equals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByHashIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where hash not equals to DEFAULT_HASH
        defaultPageUrlShouldNotBeFound("hash.notEquals=" + DEFAULT_HASH);

        // Get all the pageUrlList where hash not equals to UPDATED_HASH
        defaultPageUrlShouldBeFound("hash.notEquals=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByHashIsInShouldWork() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where hash in DEFAULT_HASH or UPDATED_HASH
        defaultPageUrlShouldBeFound("hash.in=" + DEFAULT_HASH + "," + UPDATED_HASH);

        // Get all the pageUrlList where hash equals to UPDATED_HASH
        defaultPageUrlShouldNotBeFound("hash.in=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where hash is not null
        defaultPageUrlShouldBeFound("hash.specified=true");

        // Get all the pageUrlList where hash is null
        defaultPageUrlShouldNotBeFound("hash.specified=false");
    }
                @Test
    @Transactional
    public void getAllPageUrlsByHashContainsSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where hash contains DEFAULT_HASH
        defaultPageUrlShouldBeFound("hash.contains=" + DEFAULT_HASH);

        // Get all the pageUrlList where hash contains UPDATED_HASH
        defaultPageUrlShouldNotBeFound("hash.contains=" + UPDATED_HASH);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByHashNotContainsSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where hash does not contain DEFAULT_HASH
        defaultPageUrlShouldNotBeFound("hash.doesNotContain=" + DEFAULT_HASH);

        // Get all the pageUrlList where hash does not contain UPDATED_HASH
        defaultPageUrlShouldBeFound("hash.doesNotContain=" + UPDATED_HASH);
    }


    @Test
    @Transactional
    public void getAllPageUrlsByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where startTime equals to DEFAULT_START_TIME
        defaultPageUrlShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the pageUrlList where startTime equals to UPDATED_START_TIME
        defaultPageUrlShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where startTime not equals to DEFAULT_START_TIME
        defaultPageUrlShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the pageUrlList where startTime not equals to UPDATED_START_TIME
        defaultPageUrlShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultPageUrlShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the pageUrlList where startTime equals to UPDATED_START_TIME
        defaultPageUrlShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllPageUrlsByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        // Get all the pageUrlList where startTime is not null
        defaultPageUrlShouldBeFound("startTime.specified=true");

        // Get all the pageUrlList where startTime is null
        defaultPageUrlShouldNotBeFound("startTime.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPageUrlShouldBeFound(String filter) throws Exception {
        restPageUrlMockMvc.perform(get("/api/page-urls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageUrl.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())));

        // Check, that the count call also returns 1
        restPageUrlMockMvc.perform(get("/api/page-urls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPageUrlShouldNotBeFound(String filter) throws Exception {
        restPageUrlMockMvc.perform(get("/api/page-urls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPageUrlMockMvc.perform(get("/api/page-urls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPageUrl() throws Exception {
        // Get the pageUrl
        restPageUrlMockMvc.perform(get("/api/page-urls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePageUrl() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        int databaseSizeBeforeUpdate = pageUrlRepository.findAll().size();

        // Update the pageUrl
        PageUrl updatedPageUrl = pageUrlRepository.findById(pageUrl.getId()).get();
        // Disconnect from session so that the updates on updatedPageUrl are not directly saved in db
        em.detach(updatedPageUrl);
        updatedPageUrl
            .isbn(UPDATED_ISBN)
            .page(UPDATED_PAGE)
            .hash(UPDATED_HASH)
            .startTime(UPDATED_START_TIME);
        PageUrlDTO pageUrlDTO = pageUrlMapper.toDto(updatedPageUrl);

        restPageUrlMockMvc.perform(put("/api/page-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pageUrlDTO)))
            .andExpect(status().isOk());

        // Validate the PageUrl in the database
        List<PageUrl> pageUrlList = pageUrlRepository.findAll();
        assertThat(pageUrlList).hasSize(databaseSizeBeforeUpdate);
        PageUrl testPageUrl = pageUrlList.get(pageUrlList.size() - 1);
        assertThat(testPageUrl.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testPageUrl.getPage()).isEqualTo(UPDATED_PAGE);
        assertThat(testPageUrl.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testPageUrl.getStartTime()).isEqualTo(UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPageUrl() throws Exception {
        int databaseSizeBeforeUpdate = pageUrlRepository.findAll().size();

        // Create the PageUrl
        PageUrlDTO pageUrlDTO = pageUrlMapper.toDto(pageUrl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageUrlMockMvc.perform(put("/api/page-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pageUrlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageUrl in the database
        List<PageUrl> pageUrlList = pageUrlRepository.findAll();
        assertThat(pageUrlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePageUrl() throws Exception {
        // Initialize the database
        pageUrlRepository.saveAndFlush(pageUrl);

        int databaseSizeBeforeDelete = pageUrlRepository.findAll().size();

        // Delete the pageUrl
        restPageUrlMockMvc.perform(delete("/api/page-urls/{id}", pageUrl.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageUrl> pageUrlList = pageUrlRepository.findAll();
        assertThat(pageUrlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
