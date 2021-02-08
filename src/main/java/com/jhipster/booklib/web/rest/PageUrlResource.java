package com.jhipster.booklib.web.rest;

import com.jhipster.booklib.service.PageUrlService;
import com.jhipster.booklib.web.rest.errors.BadRequestAlertException;
import com.jhipster.booklib.service.dto.PageUrlDTO;
import com.jhipster.booklib.service.dto.PageUrlCriteria;
import com.jhipster.booklib.service.PageUrlQueryService;

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
 * REST controller for managing {@link com.jhipster.booklib.domain.PageUrl}.
 */
@RestController
@RequestMapping("/api")
public class PageUrlResource {

    private final Logger log = LoggerFactory.getLogger(PageUrlResource.class);

    private static final String ENTITY_NAME = "pageUrl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PageUrlService pageUrlService;

    private final PageUrlQueryService pageUrlQueryService;

    public PageUrlResource(PageUrlService pageUrlService, PageUrlQueryService pageUrlQueryService) {
        this.pageUrlService = pageUrlService;
        this.pageUrlQueryService = pageUrlQueryService;
    }

    /**
     * {@code POST  /page-urls} : Create a new pageUrl.
     *
     * @param pageUrlDTO the pageUrlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pageUrlDTO, or with status {@code 400 (Bad Request)} if the pageUrl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/page-urls")
    public ResponseEntity<PageUrlDTO> createPageUrl(@RequestBody PageUrlDTO pageUrlDTO) throws URISyntaxException {
        log.debug("REST request to save PageUrl : {}", pageUrlDTO);
        if (pageUrlDTO.getId() != null) {
            throw new BadRequestAlertException("A new pageUrl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PageUrlDTO result = pageUrlService.save(pageUrlDTO);
        return ResponseEntity.created(new URI("/api/page-urls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /page-urls} : Updates an existing pageUrl.
     *
     * @param pageUrlDTO the pageUrlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pageUrlDTO,
     * or with status {@code 400 (Bad Request)} if the pageUrlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pageUrlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/page-urls")
    public ResponseEntity<PageUrlDTO> updatePageUrl(@RequestBody PageUrlDTO pageUrlDTO) throws URISyntaxException {
        log.debug("REST request to update PageUrl : {}", pageUrlDTO);
        if (pageUrlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PageUrlDTO result = pageUrlService.save(pageUrlDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pageUrlDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /page-urls} : get all the pageUrls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pageUrls in body.
     */
    @GetMapping("/page-urls")
    public ResponseEntity<List<PageUrlDTO>> getAllPageUrls(PageUrlCriteria criteria) {
        log.debug("REST request to get PageUrls by criteria: {}", criteria);
        List<PageUrlDTO> entityList = pageUrlQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /page-urls/count} : count all the pageUrls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/page-urls/count")
    public ResponseEntity<Long> countPageUrls(PageUrlCriteria criteria) {
        log.debug("REST request to count PageUrls by criteria: {}", criteria);
        return ResponseEntity.ok().body(pageUrlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /page-urls/:id} : get the "id" pageUrl.
     *
     * @param id the id of the pageUrlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pageUrlDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/page-urls/{id}")
    public ResponseEntity<PageUrlDTO> getPageUrl(@PathVariable Long id) {
        log.debug("REST request to get PageUrl : {}", id);
        Optional<PageUrlDTO> pageUrlDTO = pageUrlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pageUrlDTO);
    }

    /**
     * {@code DELETE  /page-urls/:id} : delete the "id" pageUrl.
     *
     * @param id the id of the pageUrlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/page-urls/{id}")
    public ResponseEntity<Void> deletePageUrl(@PathVariable Long id) {
        log.debug("REST request to delete PageUrl : {}", id);
        pageUrlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
