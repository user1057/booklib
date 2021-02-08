package com.jhipster.booklib.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.booklib.web.rest.TestUtil;

public class PageContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageContent.class);
        PageContent pageContent1 = new PageContent();
        pageContent1.setId(1L);
        PageContent pageContent2 = new PageContent();
        pageContent2.setId(pageContent1.getId());
        assertThat(pageContent1).isEqualTo(pageContent2);
        pageContent2.setId(2L);
        assertThat(pageContent1).isNotEqualTo(pageContent2);
        pageContent1.setId(null);
        assertThat(pageContent1).isNotEqualTo(pageContent2);
    }
}
