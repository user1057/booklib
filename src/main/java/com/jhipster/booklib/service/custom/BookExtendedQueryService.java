package com.jhipster.booklib.service.custom;

import com.jhipster.booklib.domain.Book;
import com.jhipster.booklib.repository.BookRepository;
import com.jhipster.booklib.service.BookQueryService;
import com.jhipster.booklib.service.dto.BookCriteria;
import com.jhipster.booklib.service.mapper.BookMapper;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookExtendedQueryService extends BookQueryService {
    private final Logger log = LoggerFactory.getLogger(BookExtendedQueryService.class);
    private final BookRepository bookRepository;

    public BookExtendedQueryService(BookRepository bookRepository, BookMapper bookMapper) {
        super(bookRepository, bookMapper);
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findBook(BookCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Book> specification = createSpecification(criteria);
        return bookRepository.findAll(specification);
    }

    public static BookCriteria criteriaIsbn(String isbn){
        StringFilter isbnFilter = new StringFilter();
        isbnFilter.setEquals(isbn);
        BookCriteria criteria = new BookCriteria();
        criteria.setIsbn(isbnFilter);

        return criteria;
    }
}
