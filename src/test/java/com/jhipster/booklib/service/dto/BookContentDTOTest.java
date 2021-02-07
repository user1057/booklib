package com.jhipster.booklib.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.booklib.web.rest.TestUtil;

public class BookContentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookContentDTO.class);
        BookContentDTO bookContentDTO1 = new BookContentDTO();
        bookContentDTO1.setId(1L);
        BookContentDTO bookContentDTO2 = new BookContentDTO();
        assertThat(bookContentDTO1).isNotEqualTo(bookContentDTO2);
        bookContentDTO2.setId(bookContentDTO1.getId());
        assertThat(bookContentDTO1).isEqualTo(bookContentDTO2);
        bookContentDTO2.setId(2L);
        assertThat(bookContentDTO1).isNotEqualTo(bookContentDTO2);
        bookContentDTO1.setId(null);
        assertThat(bookContentDTO1).isNotEqualTo(bookContentDTO2);
    }
}
