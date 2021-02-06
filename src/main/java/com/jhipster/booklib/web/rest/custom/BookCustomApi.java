package com.jhipster.booklib.web.rest.custom;

import com.google.common.io.ByteSource;
import com.jhipster.booklib.web.api.BookApiDelegate;
import com.jhipster.booklib.web.api.model.Book;
import io.github.jhipster.web.util.HeaderUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

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

/*        Optional<AttachmentWithContentMDL> awc = attachmentCustomService.findMDL(id);

        InputStream targetStream = ByteSource.wrap(awc.get().getAttachmentContent().getData()).openStream();

        response.setContentType(awc.get().getMimeType());
        response.setContentLength(targetStream.available());
        IOUtils.copy(targetStream, response.getOutputStream());*/


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
