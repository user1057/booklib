package com.jhipster.booklib.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PageContentMapperTest {

    private PageContentMapper pageContentMapper;

    @BeforeEach
    public void setUp() {
        pageContentMapper = new PageContentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pageContentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pageContentMapper.fromId(null)).isNull();
    }
}
