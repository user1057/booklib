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

import com.jhipster.booklib.domain.PageUrl;
import com.jhipster.booklib.domain.*; // for static metamodels
import com.jhipster.booklib.repository.PageUrlRepository;
import com.jhipster.booklib.service.dto.PageUrlCriteria;
import com.jhipster.booklib.service.dto.PageUrlDTO;
import com.jhipster.booklib.service.mapper.PageUrlMapper;

/**
 * Service for executing complex queries for {@link PageUrl} entities in the database.
 * The main input is a {@link PageUrlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PageUrlDTO} or a {@link Page} of {@link PageUrlDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PageUrlQueryService extends QueryService<PageUrl> {

    private final Logger log = LoggerFactory.getLogger(PageUrlQueryService.class);

    private final PageUrlRepository pageUrlRepository;

    private final PageUrlMapper pageUrlMapper;

    public PageUrlQueryService(PageUrlRepository pageUrlRepository, PageUrlMapper pageUrlMapper) {
        this.pageUrlRepository = pageUrlRepository;
        this.pageUrlMapper = pageUrlMapper;
    }

    /**
     * Return a {@link List} of {@link PageUrlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PageUrlDTO> findByCriteria(PageUrlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PageUrl> specification = createSpecification(criteria);
        return pageUrlMapper.toDto(pageUrlRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PageUrlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PageUrlDTO> findByCriteria(PageUrlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PageUrl> specification = createSpecification(criteria);
        return pageUrlRepository.findAll(specification, page)
            .map(pageUrlMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PageUrlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PageUrl> specification = createSpecification(criteria);
        return pageUrlRepository.count(specification);
    }

    /**
     * Function to convert {@link PageUrlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PageUrl> createSpecification(PageUrlCriteria criteria) {
        Specification<PageUrl> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PageUrl_.id));
            }
            if (criteria.getIsbn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsbn(), PageUrl_.isbn));
            }
            if (criteria.getPage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPage(), PageUrl_.page));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), PageUrl_.hash));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), PageUrl_.startTime));
            }
        }
        return specification;
    }
}
