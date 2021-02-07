package com.jhipster.booklib.service.mapper;


import com.jhipster.booklib.domain.*;
import com.jhipster.booklib.service.dto.BookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = {BookContentMapper.class})
public interface BookMapper extends EntityMapper<BookDTO, Book> {

    @Mapping(source = "content.id", target = "contentId")
    BookDTO toDto(Book book);

    @Mapping(source = "contentId", target = "content")
    Book toEntity(BookDTO bookDTO);

    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
