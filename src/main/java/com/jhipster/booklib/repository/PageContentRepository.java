package com.jhipster.booklib.repository;

import com.jhipster.booklib.domain.PageContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PageContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageContentRepository extends JpaRepository<PageContent, Long>, JpaSpecificationExecutor<PageContent> {
}
