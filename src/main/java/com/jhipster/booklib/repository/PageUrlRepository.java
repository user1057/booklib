package com.jhipster.booklib.repository;

import com.jhipster.booklib.domain.PageUrl;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PageUrl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageUrlRepository extends JpaRepository<PageUrl, Long>, JpaSpecificationExecutor<PageUrl> {
}
