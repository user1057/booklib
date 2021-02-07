package com.jhipster.booklib.repository;

import com.jhipster.booklib.domain.BookContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BookContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookContentRepository extends JpaRepository<BookContent, Long> {
}
