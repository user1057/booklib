package com.jhipster.booklib.service;

import com.jhipster.booklib.domain.PageUrl;
import com.jhipster.booklib.repository.PageUrlRepository;
import com.jhipster.booklib.service.dto.PageUrlDTO;
import com.jhipster.booklib.service.mapper.PageUrlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PageUrl}.
 */
@Service
@Transactional
public class PageUrlService {

    private final Logger log = LoggerFactory.getLogger(PageUrlService.class);

    private final PageUrlRepository pageUrlRepository;

    private final PageUrlMapper pageUrlMapper;

    public PageUrlService(PageUrlRepository pageUrlRepository, PageUrlMapper pageUrlMapper) {
        this.pageUrlRepository = pageUrlRepository;
        this.pageUrlMapper = pageUrlMapper;
    }

    /**
     * Save a pageUrl.
     *
     * @param pageUrlDTO the entity to save.
     * @return the persisted entity.
     */
    public PageUrlDTO save(PageUrlDTO pageUrlDTO) {
        log.debug("Request to save PageUrl : {}", pageUrlDTO);
        PageUrl pageUrl = pageUrlMapper.toEntity(pageUrlDTO);
        pageUrl = pageUrlRepository.save(pageUrl);
        return pageUrlMapper.toDto(pageUrl);
    }

    /**
     * Get all the pageUrls.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PageUrlDTO> findAll() {
        log.debug("Request to get all PageUrls");
        return pageUrlRepository.findAll().stream()
            .map(pageUrlMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pageUrl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PageUrlDTO> findOne(Long id) {
        log.debug("Request to get PageUrl : {}", id);
        return pageUrlRepository.findById(id)
            .map(pageUrlMapper::toDto);
    }

    /**
     * Delete the pageUrl by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PageUrl : {}", id);
        pageUrlRepository.deleteById(id);
    }
}
