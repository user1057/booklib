package com.jhipster.booklib.web.rest.custom;

import com.jhipster.booklib.web.api.PageApiDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public class PageApiDelegateImpl implements PageApiDelegate {
    private final Logger log = LoggerFactory.getLogger(BookApiDelegateImpl.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NativeWebRequest request;

    public PageApiDelegateImpl(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<String> getPageUrl(String isbn, Integer page) {
        return null;
    }
}
