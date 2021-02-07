package com.jhipster.booklib.web.rest;

import com.jhipster.booklib.service.BookContentService;
import com.jhipster.booklib.web.rest.errors.BadRequestAlertException;
import com.jhipster.booklib.service.dto.BookContentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.jhipster.booklib.domain.BookContent}.
 */
@RestController
@RequestMapping("/api")
public class BookContentResource {

    private final Logger log = LoggerFactory.getLogger(BookContentResource.class);

    private static final String ENTITY_NAME = "bookContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookContentService bookContentService;

    public BookContentResource(BookContentService bookContentService) {
        this.bookContentService = bookContentService;
    }

    /**
     * {@code POST  /book-contents} : Create a new bookContent.
     *
     * @param bookContentDTO the bookContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookContentDTO, or with status {@code 400 (Bad Request)} if the bookContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/book-contents")
    public ResponseEntity<BookContentDTO> createBookContent(@Valid @RequestBody BookContentDTO bookContentDTO) throws URISyntaxException {
        log.debug("REST request to save BookContent : {}", bookContentDTO);
        if (bookContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookContentDTO result = bookContentService.save(bookContentDTO);
        return ResponseEntity.created(new URI("/api/book-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /book-contents} : Updates an existing bookContent.
     *
     * @param bookContentDTO the bookContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookContentDTO,
     * or with status {@code 400 (Bad Request)} if the bookContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/book-contents")
    public ResponseEntity<BookContentDTO> updateBookContent(@Valid @RequestBody BookContentDTO bookContentDTO) throws URISyntaxException {
        log.debug("REST request to update BookContent : {}", bookContentDTO);
        if (bookContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookContentDTO result = bookContentService.save(bookContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bookContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /book-contents} : get all the bookContents.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookContents in body.
     */
    @GetMapping("/book-contents")
    public List<BookContentDTO> getAllBookContents(@RequestParam(required = false) String filter) {
        if ("bookcontent-is-null".equals(filter)) {
            log.debug("REST request to get all BookContents where bookContent is null");
            return bookContentService.findAllWhereBookContentIsNull();
        }
        log.debug("REST request to get all BookContents");
        return bookContentService.findAll();
    }

    /**
     * {@code GET  /book-contents/:id} : get the "id" bookContent.
     *
     * @param id the id of the bookContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/book-contents/{id}")
    public ResponseEntity<BookContentDTO> getBookContent(@PathVariable Long id) {
        log.debug("REST request to get BookContent : {}", id);
        Optional<BookContentDTO> bookContentDTO = bookContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookContentDTO);
    }

    /**
     * {@code DELETE  /book-contents/:id} : delete the "id" bookContent.
     *
     * @param id the id of the bookContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/book-contents/{id}")
    public ResponseEntity<Void> deleteBookContent(@PathVariable Long id) {
        log.debug("REST request to delete BookContent : {}", id);
        bookContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
