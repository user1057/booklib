package com.jhipster.booklib.web.rest;

import com.jhipster.booklib.BooklibApp;
import com.jhipster.booklib.domain.PageContent;
import com.jhipster.booklib.repository.PageContentRepository;
import com.jhipster.booklib.service.PageContentService;
import com.jhipster.booklib.service.dto.PageContentDTO;
import com.jhipster.booklib.service.mapper.PageContentMapper;
import com.jhipster.booklib.service.dto.PageContentCriteria;
import com.jhipster.booklib.service.PageContentQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PageContentResource} REST controller.
 */
@SpringBootTest(classes = BooklibApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PageContentResourceIT {

    private static final String DEFAULT_ISBN = "AAAAAAAAAA";
    private static final String UPDATED_ISBN = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;
    private static final Integer SMALLER_PAGE = 1 - 1;

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    @Autowired
    private PageContentRepository pageContentRepository;

    @Autowired
    private PageContentMapper pageContentMapper;

    @Autowired
    private PageContentService pageContentService;

    @Autowired
    private PageContentQueryService pageContentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPageContentMockMvc;

    private PageContent pageContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageContent createEntity(EntityManager em) {
        PageContent pageContent = new PageContent()
            .isbn(DEFAULT_ISBN)
            .page(DEFAULT_PAGE)
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE);
        return pageContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PageContent createUpdatedEntity(EntityManager em) {
        PageContent pageContent = new PageContent()
            .isbn(UPDATED_ISBN)
            .page(UPDATED_PAGE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        return pageContent;
    }

    @BeforeEach
    public void initTest() {
        pageContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createPageContent() throws Exception {
        int databaseSizeBeforeCreate = pageContentRepository.findAll().size();

        // Create the PageContent
        PageContentDTO pageContentDTO = pageContentMapper.toDto(pageContent);
        restPageContentMockMvc.perform(post("/api/page-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pageContentDTO)))
            .andExpect(status().isCreated());

        // Validate the PageContent in the database
        List<PageContent> pageContentList = pageContentRepository.findAll();
        assertThat(pageContentList).hasSize(databaseSizeBeforeCreate + 1);
        PageContent testPageContent = pageContentList.get(pageContentList.size() - 1);
        assertThat(testPageContent.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testPageContent.getPage()).isEqualTo(DEFAULT_PAGE);
        assertThat(testPageContent.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPageContent.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPageContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pageContentRepository.findAll().size();

        // Create the PageContent with an existing ID
        pageContent.setId(1L);
        PageContentDTO pageContentDTO = pageContentMapper.toDto(pageContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageContentMockMvc.perform(post("/api/page-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pageContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageContent in the database
        List<PageContent> pageContentList = pageContentRepository.findAll();
        assertThat(pageContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPageContents() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList
        restPageContentMockMvc.perform(get("/api/page-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getPageContent() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get the pageContent
        restPageContentMockMvc.perform(get("/api/page-contents/{id}", pageContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pageContent.getId().intValue()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)));
    }


    @Test
    @Transactional
    public void getPageContentsByIdFiltering() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        Long id = pageContent.getId();

        defaultPageContentShouldBeFound("id.equals=" + id);
        defaultPageContentShouldNotBeFound("id.notEquals=" + id);

        defaultPageContentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPageContentShouldNotBeFound("id.greaterThan=" + id);

        defaultPageContentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPageContentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPageContentsByIsbnIsEqualToSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where isbn equals to DEFAULT_ISBN
        defaultPageContentShouldBeFound("isbn.equals=" + DEFAULT_ISBN);

        // Get all the pageContentList where isbn equals to UPDATED_ISBN
        defaultPageContentShouldNotBeFound("isbn.equals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllPageContentsByIsbnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where isbn not equals to DEFAULT_ISBN
        defaultPageContentShouldNotBeFound("isbn.notEquals=" + DEFAULT_ISBN);

        // Get all the pageContentList where isbn not equals to UPDATED_ISBN
        defaultPageContentShouldBeFound("isbn.notEquals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllPageContentsByIsbnIsInShouldWork() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where isbn in DEFAULT_ISBN or UPDATED_ISBN
        defaultPageContentShouldBeFound("isbn.in=" + DEFAULT_ISBN + "," + UPDATED_ISBN);

        // Get all the pageContentList where isbn equals to UPDATED_ISBN
        defaultPageContentShouldNotBeFound("isbn.in=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllPageContentsByIsbnIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where isbn is not null
        defaultPageContentShouldBeFound("isbn.specified=true");

        // Get all the pageContentList where isbn is null
        defaultPageContentShouldNotBeFound("isbn.specified=false");
    }
                @Test
    @Transactional
    public void getAllPageContentsByIsbnContainsSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where isbn contains DEFAULT_ISBN
        defaultPageContentShouldBeFound("isbn.contains=" + DEFAULT_ISBN);

        // Get all the pageContentList where isbn contains UPDATED_ISBN
        defaultPageContentShouldNotBeFound("isbn.contains=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllPageContentsByIsbnNotContainsSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where isbn does not contain DEFAULT_ISBN
        defaultPageContentShouldNotBeFound("isbn.doesNotContain=" + DEFAULT_ISBN);

        // Get all the pageContentList where isbn does not contain UPDATED_ISBN
        defaultPageContentShouldBeFound("isbn.doesNotContain=" + UPDATED_ISBN);
    }


    @Test
    @Transactional
    public void getAllPageContentsByPageIsEqualToSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where page equals to DEFAULT_PAGE
        defaultPageContentShouldBeFound("page.equals=" + DEFAULT_PAGE);

        // Get all the pageContentList where page equals to UPDATED_PAGE
        defaultPageContentShouldNotBeFound("page.equals=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageContentsByPageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where page not equals to DEFAULT_PAGE
        defaultPageContentShouldNotBeFound("page.notEquals=" + DEFAULT_PAGE);

        // Get all the pageContentList where page not equals to UPDATED_PAGE
        defaultPageContentShouldBeFound("page.notEquals=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageContentsByPageIsInShouldWork() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where page in DEFAULT_PAGE or UPDATED_PAGE
        defaultPageContentShouldBeFound("page.in=" + DEFAULT_PAGE + "," + UPDATED_PAGE);

        // Get all the pageContentList where page equals to UPDATED_PAGE
        defaultPageContentShouldNotBeFound("page.in=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageContentsByPageIsNullOrNotNull() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where page is not null
        defaultPageContentShouldBeFound("page.specified=true");

        // Get all the pageContentList where page is null
        defaultPageContentShouldNotBeFound("page.specified=false");
    }

    @Test
    @Transactional
    public void getAllPageContentsByPageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where page is greater than or equal to DEFAULT_PAGE
        defaultPageContentShouldBeFound("page.greaterThanOrEqual=" + DEFAULT_PAGE);

        // Get all the pageContentList where page is greater than or equal to UPDATED_PAGE
        defaultPageContentShouldNotBeFound("page.greaterThanOrEqual=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageContentsByPageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where page is less than or equal to DEFAULT_PAGE
        defaultPageContentShouldBeFound("page.lessThanOrEqual=" + DEFAULT_PAGE);

        // Get all the pageContentList where page is less than or equal to SMALLER_PAGE
        defaultPageContentShouldNotBeFound("page.lessThanOrEqual=" + SMALLER_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageContentsByPageIsLessThanSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where page is less than DEFAULT_PAGE
        defaultPageContentShouldNotBeFound("page.lessThan=" + DEFAULT_PAGE);

        // Get all the pageContentList where page is less than UPDATED_PAGE
        defaultPageContentShouldBeFound("page.lessThan=" + UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void getAllPageContentsByPageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        // Get all the pageContentList where page is greater than DEFAULT_PAGE
        defaultPageContentShouldNotBeFound("page.greaterThan=" + DEFAULT_PAGE);

        // Get all the pageContentList where page is greater than SMALLER_PAGE
        defaultPageContentShouldBeFound("page.greaterThan=" + SMALLER_PAGE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPageContentShouldBeFound(String filter) throws Exception {
        restPageContentMockMvc.perform(get("/api/page-contents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))));

        // Check, that the count call also returns 1
        restPageContentMockMvc.perform(get("/api/page-contents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPageContentShouldNotBeFound(String filter) throws Exception {
        restPageContentMockMvc.perform(get("/api/page-contents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPageContentMockMvc.perform(get("/api/page-contents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPageContent() throws Exception {
        // Get the pageContent
        restPageContentMockMvc.perform(get("/api/page-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePageContent() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        int databaseSizeBeforeUpdate = pageContentRepository.findAll().size();

        // Update the pageContent
        PageContent updatedPageContent = pageContentRepository.findById(pageContent.getId()).get();
        // Disconnect from session so that the updates on updatedPageContent are not directly saved in db
        em.detach(updatedPageContent);
        updatedPageContent
            .isbn(UPDATED_ISBN)
            .page(UPDATED_PAGE)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        PageContentDTO pageContentDTO = pageContentMapper.toDto(updatedPageContent);

        restPageContentMockMvc.perform(put("/api/page-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pageContentDTO)))
            .andExpect(status().isOk());

        // Validate the PageContent in the database
        List<PageContent> pageContentList = pageContentRepository.findAll();
        assertThat(pageContentList).hasSize(databaseSizeBeforeUpdate);
        PageContent testPageContent = pageContentList.get(pageContentList.size() - 1);
        assertThat(testPageContent.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testPageContent.getPage()).isEqualTo(UPDATED_PAGE);
        assertThat(testPageContent.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPageContent.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPageContent() throws Exception {
        int databaseSizeBeforeUpdate = pageContentRepository.findAll().size();

        // Create the PageContent
        PageContentDTO pageContentDTO = pageContentMapper.toDto(pageContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPageContentMockMvc.perform(put("/api/page-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pageContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PageContent in the database
        List<PageContent> pageContentList = pageContentRepository.findAll();
        assertThat(pageContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePageContent() throws Exception {
        // Initialize the database
        pageContentRepository.saveAndFlush(pageContent);

        int databaseSizeBeforeDelete = pageContentRepository.findAll().size();

        // Delete the pageContent
        restPageContentMockMvc.perform(delete("/api/page-contents/{id}", pageContent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PageContent> pageContentList = pageContentRepository.findAll();
        assertThat(pageContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
