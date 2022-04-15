package com.project.mydraw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.project.mydraw.web.rest.TestUtil;

public class BackgroundColorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BackgroundColorDTO.class);
        BackgroundColorDTO backgroundColorDTO1 = new BackgroundColorDTO();
        backgroundColorDTO1.setId(1L);
        BackgroundColorDTO backgroundColorDTO2 = new BackgroundColorDTO();
        assertThat(backgroundColorDTO1).isNotEqualTo(backgroundColorDTO2);
        backgroundColorDTO2.setId(backgroundColorDTO1.getId());
        assertThat(backgroundColorDTO1).isEqualTo(backgroundColorDTO2);
        backgroundColorDTO2.setId(2L);
        assertThat(backgroundColorDTO1).isNotEqualTo(backgroundColorDTO2);
        backgroundColorDTO1.setId(null);
        assertThat(backgroundColorDTO1).isNotEqualTo(backgroundColorDTO2);
    }
}
