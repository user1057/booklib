package com.jhipster.booklib.service.mapper;


import com.jhipster.booklib.domain.*;
import com.jhipster.booklib.service.dto.PageUrlDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PageUrl} and its DTO {@link PageUrlDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PageUrlMapper extends EntityMapper<PageUrlDTO, PageUrl> {



    default PageUrl fromId(Long id) {
        if (id == null) {
            return null;
        }
        PageUrl pageUrl = new PageUrl();
        pageUrl.setId(id);
        return pageUrl;
    }
}
