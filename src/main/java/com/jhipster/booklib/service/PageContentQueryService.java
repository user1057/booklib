package com.jhipster.booklib.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.jhipster.booklib.domain.PageContent;
import com.jhipster.booklib.domain.*; // for static metamodels
import com.jhipster.booklib.repository.PageContentRepository;
import com.jhipster.booklib.service.dto.PageContentCriteria;
import com.jhipster.booklib.service.dto.PageContentDTO;
import com.jhipster.booklib.service.mapper.PageContentMapper;

/**
 * Service for executing complex queries for {@link PageContent} entities in the database.
 * The main input is a {@link PageContentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PageContentDTO} or a {@link Page} of {@link PageContentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PageContentQueryService extends QueryService<PageContent> {

    private final Logger log = LoggerFactory.getLogger(PageContentQueryService.class);

    private final PageContentRepository pageContentRepository;

    private final PageContentMapper pageContentMapper;

    public PageContentQueryService(PageContentRepository pageContentRepository, PageContentMapper pageContentMapper) {
        this.pageContentRepository = pageContentRepository;
        this.pageContentMapper = pageContentMapper;
    }

    /**
     * Return a {@link List} of {@link PageContentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PageContentDTO> findByCriteria(PageContentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PageContent> specification = createSpecification(criteria);
        return pageContentMapper.toDto(pageContentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PageContentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PageContentDTO> findByCriteria(PageContentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PageContent> specification = createSpecification(criteria);
        return pageContentRepository.findAll(specification, page)
            .map(pageContentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PageContentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PageContent> specification = createSpecification(criteria);
        return pageContentRepository.count(specification);
    }

    /**
     * Function to convert {@link PageContentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PageContent> createSpecification(PageContentCriteria criteria) {
        Specification<PageContent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PageContent_.id));
            }
            if (criteria.getIsbn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsbn(), PageContent_.isbn));
            }
            if (criteria.getPage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPage(), PageContent_.page));
            }
        }
        return specification;
    }
}
