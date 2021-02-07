package com.jhipster.booklib.service.custom;

import com.jhipster.booklib.domain.Book;
import com.jhipster.booklib.domain.BookContent;
import com.jhipster.booklib.repository.BookRepository;
import com.jhipster.booklib.service.mapper.custom.BookMDLMapper;
import com.jhipster.booklib.web.api.model.BookMDL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookCustomService {
    private final Logger log = LoggerFactory.getLogger(BookCustomService.class);
    private final BookMDLMapper bookMDLMapper;
    private final BookRepository bookRepository;

    public BookCustomService(BookRepository bookRepository, BookMDLMapper bookMDLMapper) {
        this.bookRepository = bookRepository;
        this.bookMDLMapper = bookMDLMapper;
    }

    public Book saveFile(String isbn, MultipartFile file) {

        Book book = new Book();
        book.setIsbn(isbn);
        book.setProcessed(false);

        BookContent bookContent = new BookContent();

        try {
            bookContent.setData(file.getBytes());
        } catch (
            IOException e) {
            log.error(e.getMessage());
        }
        bookContent.setDataContentType(file.getContentType());
        bookContent.setBookContent(book);

        book.setContent(bookContent);

        book = bookRepository.save(book);

        return book;
    }

    @Transactional(readOnly = true)
    public List<BookMDL> findAll() {
        return bookRepository.findAll().stream()
            .map(bookMDLMapper::toDto).collect(Collectors.toList());
    }
}
