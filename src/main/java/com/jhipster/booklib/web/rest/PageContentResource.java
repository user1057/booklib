package com.jhipster.booklib.web.rest;

import com.jhipster.booklib.service.PageContentService;
import com.jhipster.booklib.web.rest.errors.BadRequestAlertException;
import com.jhipster.booklib.service.dto.PageContentDTO;
import com.jhipster.booklib.service.dto.PageContentCriteria;
import com.jhipster.booklib.service.PageContentQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jhipster.booklib.domain.PageContent}.
 */
@RestController
@RequestMapping("/api")
public class PageContentResource {

    private final Logger log = LoggerFactory.getLogger(PageContentResource.class);

    private static final String ENTITY_NAME = "pageContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageContentService pageContentService;

    private final PageContentQueryService pageContentQueryService;

    public PageContentResource(PageContentService pageContentService, PageContentQueryService pageContentQueryService) {
        this.pageContentService = pageContentService;
        this.pageContentQueryService = pageContentQueryService;
    }

    /**
     * {@code POST  /page-contents} : Create a new pageContent.
     *
     * @param pageContentDTO the pageContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageContentDTO, or with status {@code 400 (Bad Request)} if the pageContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-contents")
    public ResponseEntity<PageContentDTO> createPageContent(@RequestBody PageContentDTO pageContentDTO) throws URISyntaxException {
        log.debug("REST request to save PageContent : {}", pageContentDTO);
        if (pageContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new pageContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageContentDTO result = pageContentService.save(pageContentDTO);
        return ResponseEntity.created(new URI("/api/page-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-contents} : Updates an existing pageContent.
     *
     * @param pageContentDTO the pageContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageContentDTO,
     * or with status {@code 400 (Bad Request)} if the pageContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-contents")
    public ResponseEntity<PageContentDTO> updatePageContent(@RequestBody PageContentDTO pageContentDTO) throws URISyntaxException {
        log.debug("REST request to update PageContent : {}", pageContentDTO);
        if (pageContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PageContentDTO result = pageContentService.save(pageContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pageContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /page-contents} : get all the pageContents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageContents in body.
     */
    @GetMapping("/page-contents")
    public ResponseEntity<List<PageContentDTO>> getAllPageContents(PageContentCriteria criteria) {
        log.debug("REST request to get PageContents by criteria: {}", criteria);
        List<PageContentDTO> entityList = pageContentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /page-contents/count} : count all the pageContents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/page-contents/count")
    public ResponseEntity<Long> countPageContents(PageContentCriteria criteria) {
        log.debug("REST request to count PageContents by criteria: {}", criteria);
        return ResponseEntity.ok().body(pageContentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /page-contents/:id} : get the "id" pageContent.
     *
     * @param id the id of the pageContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-contents/{id}")
    public ResponseEntity<PageContentDTO> getPageContent(@PathVariable Long id) {
        log.debug("REST request to get PageContent : {}", id);
        Optional<PageContentDTO> pageContentDTO = pageContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageContentDTO);
    }

    /**
     * {@code DELETE  /page-contents/:id} : delete the "id" pageContent.
     *
     * @param id the id of the pageContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-contents/{id}")
    public ResponseEntity<Void> deletePageContent(@PathVariable Long id) {
        log.debug("REST request to delete PageContent : {}", id);
        pageContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
