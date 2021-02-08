package com.jhipster.booklib.service.mapper;


import com.jhipster.booklib.domain.*;
import com.jhipster.booklib.service.dto.PageContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PageContent} and its DTO {@link PageContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PageContentMapper extends EntityMapper<PageContentDTO, PageContent> {



    default PageContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        PageContent pageContent = new PageContent();
        pageContent.setId(id);
        return pageContent;
    }
}
