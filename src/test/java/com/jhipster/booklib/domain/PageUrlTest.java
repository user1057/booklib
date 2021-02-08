package com.jhipster.booklib.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.booklib.web.rest.TestUtil;

public class PageUrlTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageUrl.class);
        PageUrl pageUrl1 = new PageUrl();
        pageUrl1.setId(1L);
        PageUrl pageUrl2 = new PageUrl();
        pageUrl2.setId(pageUrl1.getId());
        assertThat(pageUrl1).isEqualTo(pageUrl2);
        pageUrl2.setId(2L);
        assertThat(pageUrl1).isNotEqualTo(pageUrl2);
        pageUrl1.setId(null);
        assertThat(pageUrl1).isNotEqualTo(pageUrl2);
    }
}
