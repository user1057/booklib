package com.jhipster.booklib.web.rest.custom;

import com.jhipster.booklib.domain.PageUrl;
import com.jhipster.booklib.repository.PageUrlRepository;
import com.jhipster.booklib.service.PageContentQueryService;
import com.jhipster.booklib.service.dto.PageContentCriteria;
import com.jhipster.booklib.service.dto.PageContentDTO;
import com.jhipster.booklib.web.api.PageApiDelegate;
import com.jhipster.booklib.web.api.model.PageMDL;
import com.jhipster.booklib.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PageApiDelegateImpl implements PageApiDelegate {
    private final Logger log = LoggerFactory.getLogger(BookApiDelegateImpl.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NativeWebRequest request;
    private final PageContentQueryService pageContentQueryService;
    private final PageUrlRepository pageUrlRepository;

    public PageApiDelegateImpl(NativeWebRequest request, PageContentQueryService pageContentQueryService, PageUrlRepository pageUrlRepository) {
        this.request = request;
        this.pageContentQueryService = pageContentQueryService;
        this.pageUrlRepository = pageUrlRepository;
    }

    @Override
    public ResponseEntity<PageMDL> getPageUrl(String isbn, Integer page) {
        log.debug("REST request to get URL of book page");

        PageContentCriteria criteria = new PageContentCriteria();

        StringFilter isbnFilter = new StringFilter();
        isbnFilter.setEquals(isbn);
        IntegerFilter pageFilter = new IntegerFilter();
        pageFilter.setEquals(page);
        criteria.setIsbn(isbnFilter);
        criteria.setPage(pageFilter);

        List<PageContentDTO> pageContentDTOList = pageContentQueryService.findByCriteria(criteria);

        if(pageContentDTOList.size()==1) {
            String hash = UUID.randomUUID().toString();

            PageUrl pageUrl = new PageUrl();
            pageUrl.setHash(hash);
            pageUrl.setIsbn(isbn);
            pageUrl.setPage(page);

            long currentTime = System.currentTimeMillis();
            Instant fromEpochMilli = Instant.ofEpochMilli(currentTime);
            System.out.println(fromEpochMilli);
            pageUrl.setStartTime(fromEpochMilli);

            pageUrlRepository.save(pageUrl);

            URI uri = null;

            try{
                uri = new URI("/url/"+hash);
            } catch (URISyntaxException e) {
                log.error("could not parse URI", e);
            }

            PageMDL pageMDL = new PageMDL();
            pageMDL.setIsbn(isbn);
            pageMDL.setPage(page);
            pageMDL.setUrl(uri.toString());

            return ResponseEntity
                .created(uri)
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, "Page", hash))
                .body(pageMDL);
        }else{
            throw new BadRequestAlertException("Missing page URL for book","PageUrl", "missingUrl");
        }
    }
}
