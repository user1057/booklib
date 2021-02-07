package com.jhipster.booklib.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BookContentMapperTest {

    private BookContentMapper bookContentMapper;

    @BeforeEach
    public void setUp() {
        bookContentMapper = new BookContentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bookContentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bookContentMapper.fromId(null)).isNull();
    }
}
