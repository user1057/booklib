package com.jhipster.booklib.web.rest;

import com.jhipster.booklib.BooklibApp;
import com.jhipster.booklib.domain.Book;
import com.jhipster.booklib.domain.BookContent;
import com.jhipster.booklib.repository.BookRepository;
import com.jhipster.booklib.service.BookService;
import com.jhipster.booklib.service.dto.BookDTO;
import com.jhipster.booklib.service.mapper.BookMapper;
import com.jhipster.booklib.service.dto.BookCriteria;
import com.jhipster.booklib.service.BookQueryService;

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
 * Integration tests for the {@link BookResource} REST controller.
 */
@SpringBootTest(classes = BooklibApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BookResourceIT {

    private static final String DEFAULT_ISBN = "AAAAAAAAAA";
    private static final String UPDATED_ISBN = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE_COUNT = 1;
    private static final Integer UPDATED_PAGE_COUNT = 2;
    private static final Integer SMALLER_PAGE_COUNT = 1 - 1;

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookQueryService bookQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMockMvc;

    private Book book;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .isbn(DEFAULT_ISBN)
            .pageCount(DEFAULT_PAGE_COUNT)
            .processed(DEFAULT_PROCESSED);
        return book;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .isbn(UPDATED_ISBN)
            .pageCount(UPDATED_PAGE_COUNT)
            .processed(UPDATED_PROCESSED);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testBook.getPageCount()).isEqualTo(DEFAULT_PAGE_COUNT);
        assertThat(testBook.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);
        BookDTO bookDTO = bookMapper.toDto(book);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].pageCount").value(hasItem(DEFAULT_PAGE_COUNT)))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN))
            .andExpect(jsonPath("$.pageCount").value(DEFAULT_PAGE_COUNT))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()));
    }


    @Test
    @Transactional
    public void getBooksByIdFiltering() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        Long id = book.getId();

        defaultBookShouldBeFound("id.equals=" + id);
        defaultBookShouldNotBeFound("id.notEquals=" + id);

        defaultBookShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.greaterThan=" + id);

        defaultBookShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBooksByIsbnIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn equals to DEFAULT_ISBN
        defaultBookShouldBeFound("isbn.equals=" + DEFAULT_ISBN);

        // Get all the bookList where isbn equals to UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.equals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn not equals to DEFAULT_ISBN
        defaultBookShouldNotBeFound("isbn.notEquals=" + DEFAULT_ISBN);

        // Get all the bookList where isbn not equals to UPDATED_ISBN
        defaultBookShouldBeFound("isbn.notEquals=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn in DEFAULT_ISBN or UPDATED_ISBN
        defaultBookShouldBeFound("isbn.in=" + DEFAULT_ISBN + "," + UPDATED_ISBN);

        // Get all the bookList where isbn equals to UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.in=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn is not null
        defaultBookShouldBeFound("isbn.specified=true");

        // Get all the bookList where isbn is null
        defaultBookShouldNotBeFound("isbn.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByIsbnContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn contains DEFAULT_ISBN
        defaultBookShouldBeFound("isbn.contains=" + DEFAULT_ISBN);

        // Get all the bookList where isbn contains UPDATED_ISBN
        defaultBookShouldNotBeFound("isbn.contains=" + UPDATED_ISBN);
    }

    @Test
    @Transactional
    public void getAllBooksByIsbnNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where isbn does not contain DEFAULT_ISBN
        defaultBookShouldNotBeFound("isbn.doesNotContain=" + DEFAULT_ISBN);

        // Get all the bookList where isbn does not contain UPDATED_ISBN
        defaultBookShouldBeFound("isbn.doesNotContain=" + UPDATED_ISBN);
    }


    @Test
    @Transactional
    public void getAllBooksByPageCountIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount equals to DEFAULT_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.equals=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount equals to UPDATED_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.equals=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByPageCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount not equals to DEFAULT_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.notEquals=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount not equals to UPDATED_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.notEquals=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByPageCountIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount in DEFAULT_PAGE_COUNT or UPDATED_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.in=" + DEFAULT_PAGE_COUNT + "," + UPDATED_PAGE_COUNT);

        // Get all the bookList where pageCount equals to UPDATED_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.in=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByPageCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is not null
        defaultBookShouldBeFound("pageCount.specified=true");

        // Get all the bookList where pageCount is null
        defaultBookShouldNotBeFound("pageCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPageCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is greater than or equal to DEFAULT_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.greaterThanOrEqual=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount is greater than or equal to UPDATED_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.greaterThanOrEqual=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByPageCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is less than or equal to DEFAULT_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.lessThanOrEqual=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount is less than or equal to SMALLER_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.lessThanOrEqual=" + SMALLER_PAGE_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByPageCountIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is less than DEFAULT_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.lessThan=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount is less than UPDATED_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.lessThan=" + UPDATED_PAGE_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByPageCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pageCount is greater than DEFAULT_PAGE_COUNT
        defaultBookShouldNotBeFound("pageCount.greaterThan=" + DEFAULT_PAGE_COUNT);

        // Get all the bookList where pageCount is greater than SMALLER_PAGE_COUNT
        defaultBookShouldBeFound("pageCount.greaterThan=" + SMALLER_PAGE_COUNT);
    }


    @Test
    @Transactional
    public void getAllBooksByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where processed equals to DEFAULT_PROCESSED
        defaultBookShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the bookList where processed equals to UPDATED_PROCESSED
        defaultBookShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllBooksByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where processed not equals to DEFAULT_PROCESSED
        defaultBookShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the bookList where processed not equals to UPDATED_PROCESSED
        defaultBookShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllBooksByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultBookShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the bookList where processed equals to UPDATED_PROCESSED
        defaultBookShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllBooksByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where processed is not null
        defaultBookShouldBeFound("processed.specified=true");

        // Get all the bookList where processed is null
        defaultBookShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        BookContent content = BookContentResourceIT.createEntity(em);
        em.persist(content);
        em.flush();
        book.setContent(content);
        bookRepository.saveAndFlush(book);
        Long contentId = content.getId();

        // Get all the bookList where content equals to contentId
        defaultBookShouldBeFound("contentId.equals=" + contentId);

        // Get all the bookList where content equals to contentId + 1
        defaultBookShouldNotBeFound("contentId.equals=" + (contentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookShouldBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].pageCount").value(hasItem(DEFAULT_PAGE_COUNT)))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())));

        // Check, that the count call also returns 1
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookShouldNotBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .isbn(UPDATED_ISBN)
            .pageCount(UPDATED_PAGE_COUNT)
            .processed(UPDATED_PROCESSED);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc.perform(put("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testBook.getPageCount()).isEqualTo(UPDATED_PAGE_COUNT);
        assertThat(testBook.isProcessed()).isEqualTo(UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc.perform(put("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
