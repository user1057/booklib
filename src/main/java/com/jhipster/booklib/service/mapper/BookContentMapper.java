package com.jhipster.booklib.service.mapper;


import com.jhipster.booklib.domain.*;
import com.jhipster.booklib.service.dto.BookContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookContent} and its DTO {@link BookContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookContentMapper extends EntityMapper<BookContentDTO, BookContent> {


    @Mapping(target = "bookContent", ignore = true)
    BookContent toEntity(BookContentDTO bookContentDTO);

    default BookContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookContent bookContent = new BookContent();
        bookContent.setId(id);
        return bookContent;
    }
}
