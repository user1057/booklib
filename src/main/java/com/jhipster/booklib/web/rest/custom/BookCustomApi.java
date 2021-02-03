package com.jhipster.booklib.web.rest.custom;

import com.jhipster.booklib.web.api.BookApiDelegate;
import com.jhipster.booklib.web.api.model.Book;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BookCustomApi implements BookApiDelegate{
    private final Logger log = LoggerFactory.getLogger(BookCustomApi.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NativeWebRequest request;

    public BookCustomApi(NativeWebRequest request){
        this.request = request;
    }

    @Override
    public ResponseEntity<Void> uploadBook(String isbn, MultipartFile file) {


        log.error("isbn = " + isbn);
        log.error("file.getName() = " + file.getOriginalFilename());

        return ResponseEntity.created(null)
            //.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, savedMDL.getId().toString()))
            .build();
    }

    @Override
    public ResponseEntity<List<Book>> getBooks(String isbn) {
        log.error("isbn = " + isbn);
        return null;
    }
}
