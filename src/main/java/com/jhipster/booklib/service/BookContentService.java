package com.jhipster.booklib.service;

import com.jhipster.booklib.domain.BookContent;
import com.jhipster.booklib.repository.BookContentRepository;
import com.jhipster.booklib.service.dto.BookContentDTO;
import com.jhipster.booklib.service.mapper.BookContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link BookContent}.
 */
@Service
@Transactional
public class BookContentService {

    private final Logger log = LoggerFactory.getLogger(BookContentService.class);

    private final BookContentRepository bookContentRepository;

    private final BookContentMapper bookContentMapper;

    public BookContentService(BookContentRepository bookContentRepository, BookContentMapper bookContentMapper) {
        this.bookContentRepository = bookContentRepository;
        this.bookContentMapper = bookContentMapper;
    }

    /**
     * Save a bookContent.
     *
     * @param bookContentDTO the entity to save.
     * @return the persisted entity.
     */
    public BookContentDTO save(BookContentDTO bookContentDTO) {
        log.debug("Request to save BookContent : {}", bookContentDTO);
        BookContent bookContent = bookContentMapper.toEntity(bookContentDTO);
        bookContent = bookContentRepository.save(bookContent);
        return bookContentMapper.toDto(bookContent);
    }

    /**
     * Get all the bookContents.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BookContentDTO> findAll() {
        log.debug("Request to get all BookContents");
        return bookContentRepository.findAll().stream()
            .map(bookContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the bookContents where BookContent is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<BookContentDTO> findAllWhereBookContentIsNull() {
        log.debug("Request to get all bookContents where BookContent is null");
        return StreamSupport
            .stream(bookContentRepository.findAll().spliterator(), false)
            .filter(bookContent -> bookContent.getBookContent() == null)
            .map(bookContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bookContent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookContentDTO> findOne(Long id) {
        log.debug("Request to get BookContent : {}", id);
        return bookContentRepository.findById(id)
            .map(bookContentMapper::toDto);
    }

    /**
     * Delete the bookContent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BookContent : {}", id);
        bookContentRepository.deleteById(id);
    }
}
