package com.project.mydraw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.project.mydraw.web.rest.TestUtil;

public class BackgroundColorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BackgroundColor.class);
        BackgroundColor backgroundColor1 = new BackgroundColor();
        backgroundColor1.setId(1L);
        BackgroundColor backgroundColor2 = new BackgroundColor();
        backgroundColor2.setId(backgroundColor1.getId());
        assertThat(backgroundColor1).isEqualTo(backgroundColor2);
        backgroundColor2.setId(2L);
        assertThat(backgroundColor1).isNotEqualTo(backgroundColor2);
        backgroundColor1.setId(null);
        assertThat(backgroundColor1).isNotEqualTo(backgroundColor2);
    }
}
