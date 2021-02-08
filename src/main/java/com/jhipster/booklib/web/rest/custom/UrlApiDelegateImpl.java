package com.jhipster.booklib.web.rest.custom;

import com.jhipster.booklib.service.PageContentQueryService;
import com.jhipster.booklib.service.PageUrlQueryService;
import com.jhipster.booklib.service.dto.PageContentCriteria;
import com.jhipster.booklib.service.dto.PageContentDTO;
import com.jhipster.booklib.service.dto.PageUrlCriteria;
import com.jhipster.booklib.service.dto.PageUrlDTO;
import com.jhipster.booklib.web.api.UrlApiDelegate;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class UrlApiDelegateImpl implements UrlApiDelegate {
    private final Logger log = LoggerFactory.getLogger(UrlApiDelegateImpl.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NativeWebRequest request;
    private final PageUrlQueryService pageUrlQueryService;
    private final PageContentQueryService pageContentQueryService;

    public UrlApiDelegateImpl(NativeWebRequest request, PageUrlQueryService pageUrlQueryService, PageContentQueryService pageContentQueryService){
        this.request = request;
        this.pageUrlQueryService = pageUrlQueryService;
        this.pageContentQueryService = pageContentQueryService;
    }

    @Override
    public ResponseEntity<Resource> getPageImage(String hash) {
        log.info("Getting page image for hash: " + hash);

        PageUrlCriteria pageUrlCriteria = new PageUrlCriteria();
        StringFilter hashFilter = new StringFilter();
        hashFilter.setEquals(hash);
        pageUrlCriteria.setHash(hashFilter);

        List<PageUrlDTO> pageUrlDTOList = pageUrlQueryService.findByCriteria(pageUrlCriteria);

        if(pageUrlDTOList.size()==1){
            PageUrlDTO pageUrlDTO = pageUrlDTOList.get(0);

            if(urlExpired(pageUrlDTO)){
                return ResponseEntity.status(410).build();
            }

            PageContentCriteria pageContentCriteria = new PageContentCriteria();
            StringFilter isbnFilter = new StringFilter();
            isbnFilter.setEquals(pageUrlDTO.getIsbn());
            IntegerFilter pageFilter = new IntegerFilter();
            pageFilter.setEquals(pageUrlDTO.getPage());
            pageContentCriteria.setIsbn(isbnFilter);
            pageContentCriteria.setPage(pageFilter);

            List<PageContentDTO> pageContentDTOS = pageContentQueryService.findByCriteria(pageContentCriteria);
            if(pageContentDTOS.size()==1){
                PageContentDTO pageContentDTO = pageContentDTOS.get(0);
                InputStream is = new ByteArrayInputStream(pageContentDTO.getData());

                InputStreamResource resource = new InputStreamResource(is);

                HttpHeaders headers = new HttpHeaders();
                return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

            }else{
                return ResponseEntity.notFound().build();
            }

        }else{
            return ResponseEntity.notFound().build();
        }
    }

    private boolean urlExpired(PageUrlDTO pageUrlDTO){
        long diff = System.currentTimeMillis() - pageUrlDTO.getStartTime().toEpochMilli();
        if(diff>(5*60*1000)){
            return true;
        }
        return false;
    }
}
