package com.jhipster.booklib.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.booklib.web.rest.TestUtil;

public class BookContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookContent.class);
        BookContent bookContent1 = new BookContent();
        bookContent1.setId(1L);
        BookContent bookContent2 = new BookContent();
        bookContent2.setId(bookContent1.getId());
        assertThat(bookContent1).isEqualTo(bookContent2);
        bookContent2.setId(2L);
        assertThat(bookContent1).isNotEqualTo(bookContent2);
        bookContent1.setId(null);
        assertThat(bookContent1).isNotEqualTo(bookContent2);
    }
}
