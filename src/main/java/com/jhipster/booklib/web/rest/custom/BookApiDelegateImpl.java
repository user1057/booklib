package com.jhipster.booklib.web.rest.custom;

import com.jhipster.booklib.domain.Book;
import com.jhipster.booklib.service.BookService;
import com.jhipster.booklib.service.custom.BookCustomService;
import com.jhipster.booklib.utils.Checker;
import com.jhipster.booklib.web.api.BookApiDelegate;
import com.jhipster.booklib.web.api.model.BookMDL;
import com.jhipster.booklib.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BookApiDelegateImpl implements BookApiDelegate {
    private final Logger log = LoggerFactory.getLogger(BookApiDelegateImpl.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NativeWebRequest request;
    private final BookCustomService bookCustomService;
    private final BookService bookService;

    public BookApiDelegateImpl(NativeWebRequest request, BookCustomService bookCustomService, BookService bookService){
        this.request = request;
        this.bookCustomService = bookCustomService;
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<List<BookMDL>> getBooks() {
        log.debug("REST request to get list of books");

        List<BookMDL> result = bookCustomService.findAll();

        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> uploadBook(String isbn, MultipartFile file){
        log.debug("REST request to upload a book with isbn: {}", isbn);

        try {
            if(file == null || file.getBytes() == null || file.getContentType() == null || file.getName() == null) {
                throw new BadRequestAlertException("A new upload has empty file","Book", "emptyfile");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!Checker.isbnValid(isbn)){
            throw new BadRequestAlertException("Isbn not valid", "Book", "invalidISBN");
        }

        Book result = bookCustomService.saveFile(isbn, file);

        return ResponseEntity.created(null)
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, "Book", result.getId().toString()))
            .build();
    }
}
