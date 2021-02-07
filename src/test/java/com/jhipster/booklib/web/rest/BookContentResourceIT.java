package com.jhipster.booklib.web.rest;

import com.jhipster.booklib.BooklibApp;
import com.jhipster.booklib.domain.BookContent;
import com.jhipster.booklib.repository.BookContentRepository;
import com.jhipster.booklib.service.BookContentService;
import com.jhipster.booklib.service.dto.BookContentDTO;
import com.jhipster.booklib.service.mapper.BookContentMapper;

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
 * Integration tests for the {@link BookContentResource} REST controller.
 */
@SpringBootTest(classes = BooklibApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BookContentResourceIT {

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    @Autowired
    private BookContentRepository bookContentRepository;

    @Autowired
    private BookContentMapper bookContentMapper;

    @Autowired
    private BookContentService bookContentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookContentMockMvc;

    private BookContent bookContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookContent createEntity(EntityManager em) {
        BookContent bookContent = new BookContent()
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE);
        return bookContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookContent createUpdatedEntity(EntityManager em) {
        BookContent bookContent = new BookContent()
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        return bookContent;
    }

    @BeforeEach
    public void initTest() {
        bookContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookContent() throws Exception {
        int databaseSizeBeforeCreate = bookContentRepository.findAll().size();

        // Create the BookContent
        BookContentDTO bookContentDTO = bookContentMapper.toDto(bookContent);
        restBookContentMockMvc.perform(post("/api/book-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookContentDTO)))
            .andExpect(status().isCreated());

        // Validate the BookContent in the database
        List<BookContent> bookContentList = bookContentRepository.findAll();
        assertThat(bookContentList).hasSize(databaseSizeBeforeCreate + 1);
        BookContent testBookContent = bookContentList.get(bookContentList.size() - 1);
        assertThat(testBookContent.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testBookContent.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBookContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookContentRepository.findAll().size();

        // Create the BookContent with an existing ID
        bookContent.setId(1L);
        BookContentDTO bookContentDTO = bookContentMapper.toDto(bookContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookContentMockMvc.perform(post("/api/book-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookContent in the database
        List<BookContent> bookContentList = bookContentRepository.findAll();
        assertThat(bookContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBookContents() throws Exception {
        // Initialize the database
        bookContentRepository.saveAndFlush(bookContent);

        // Get all the bookContentList
        restBookContentMockMvc.perform(get("/api/book-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getBookContent() throws Exception {
        // Initialize the database
        bookContentRepository.saveAndFlush(bookContent);

        // Get the bookContent
        restBookContentMockMvc.perform(get("/api/book-contents/{id}", bookContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookContent.getId().intValue()))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingBookContent() throws Exception {
        // Get the bookContent
        restBookContentMockMvc.perform(get("/api/book-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookContent() throws Exception {
        // Initialize the database
        bookContentRepository.saveAndFlush(bookContent);

        int databaseSizeBeforeUpdate = bookContentRepository.findAll().size();

        // Update the bookContent
        BookContent updatedBookContent = bookContentRepository.findById(bookContent.getId()).get();
        // Disconnect from session so that the updates on updatedBookContent are not directly saved in db
        em.detach(updatedBookContent);
        updatedBookContent
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        BookContentDTO bookContentDTO = bookContentMapper.toDto(updatedBookContent);

        restBookContentMockMvc.perform(put("/api/book-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookContentDTO)))
            .andExpect(status().isOk());

        // Validate the BookContent in the database
        List<BookContent> bookContentList = bookContentRepository.findAll();
        assertThat(bookContentList).hasSize(databaseSizeBeforeUpdate);
        BookContent testBookContent = bookContentList.get(bookContentList.size() - 1);
        assertThat(testBookContent.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testBookContent.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBookContent() throws Exception {
        int databaseSizeBeforeUpdate = bookContentRepository.findAll().size();

        // Create the BookContent
        BookContentDTO bookContentDTO = bookContentMapper.toDto(bookContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookContentMockMvc.perform(put("/api/book-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookContent in the database
        List<BookContent> bookContentList = bookContentRepository.findAll();
        assertThat(bookContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookContent() throws Exception {
        // Initialize the database
        bookContentRepository.saveAndFlush(bookContent);

        int databaseSizeBeforeDelete = bookContentRepository.findAll().size();

        // Delete the bookContent
        restBookContentMockMvc.perform(delete("/api/book-contents/{id}", bookContent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookContent> bookContentList = bookContentRepository.findAll();
        assertThat(bookContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
