package com.jhipster.booklib.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PageUrlMapperTest {

    private PageUrlMapper pageUrlMapper;

    @BeforeEach
    public void setUp() {
        pageUrlMapper = new PageUrlMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pageUrlMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pageUrlMapper.fromId(null)).isNull();
    }
}
