package com.jhipster.booklib.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.booklib.web.rest.TestUtil;

public class PageUrlDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageUrlDTO.class);
        PageUrlDTO pageUrlDTO1 = new PageUrlDTO();
        pageUrlDTO1.setId(1L);
        PageUrlDTO pageUrlDTO2 = new PageUrlDTO();
        assertThat(pageUrlDTO1).isNotEqualTo(pageUrlDTO2);
        pageUrlDTO2.setId(pageUrlDTO1.getId());
        assertThat(pageUrlDTO1).isEqualTo(pageUrlDTO2);
        pageUrlDTO2.setId(2L);
        assertThat(pageUrlDTO1).isNotEqualTo(pageUrlDTO2);
        pageUrlDTO1.setId(null);
        assertThat(pageUrlDTO1).isNotEqualTo(pageUrlDTO2);
    }
}
