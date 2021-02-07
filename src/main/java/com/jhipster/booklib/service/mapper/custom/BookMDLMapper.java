package com.jhipster.booklib.service.mapper.custom;

import com.jhipster.booklib.domain.Book;
import com.jhipster.booklib.service.mapper.EntityMapper;
import com.jhipster.booklib.web.api.model.BookMDL;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMDLMapper extends EntityMapper<BookMDL, Book> {
}
