package com.jhipster.booklib.web.rest.custom;

import com.jhipster.booklib.web.api.BookApiDelegate;
import com.jhipster.booklib.web.api.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;

@Service
public class BookApiDelegateImpl implements BookApiDelegate {
    private final Logger log = LoggerFactory.getLogger(BookApiDelegateImpl.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NativeWebRequest request;

    public BookApiDelegateImpl(NativeWebRequest request){
        this.request = request;
    }

    @Override
    public ResponseEntity<List<Book>> getBooks(String isbn) {
        return null;
    }
}
