package com.jhipster.booklib.service;

import com.jhipster.booklib.domain.PageContent;
import com.jhipster.booklib.repository.PageContentRepository;
import com.jhipster.booklib.service.dto.PageContentDTO;
import com.jhipster.booklib.service.mapper.PageContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PageContent}.
 */
@Service
@Transactional
public class PageContentService {

    private final Logger log = LoggerFactory.getLogger(PageContentService.class);

    private final PageContentRepository pageContentRepository;

    private final PageContentMapper pageContentMapper;

    public PageContentService(PageContentRepository pageContentRepository, PageContentMapper pageContentMapper) {
        this.pageContentRepository = pageContentRepository;
        this.pageContentMapper = pageContentMapper;
    }

    /**
     * Save a pageContent.
     *
     * @param pageContentDTO the entity to save.
     * @return the persisted entity.
     */
    public PageContentDTO save(PageContentDTO pageContentDTO) {
        log.debug("Request to save PageContent : {}", pageContentDTO);
        PageContent pageContent = pageContentMapper.toEntity(pageContentDTO);
        pageContent = pageContentRepository.save(pageContent);
        return pageContentMapper.toDto(pageContent);
    }

    /**
     * Get all the pageContents.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PageContentDTO> findAll() {
        log.debug("Request to get all PageContents");
        return pageContentRepository.findAll().stream()
            .map(pageContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pageContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PageContentDTO> findOne(Long id) {
        log.debug("Request to get PageContent : {}", id);
        return pageContentRepository.findById(id)
            .map(pageContentMapper::toDto);
    }

    /**
     * Delete the pageContent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PageContent : {}", id);
        pageContentRepository.deleteById(id);
    }
}
