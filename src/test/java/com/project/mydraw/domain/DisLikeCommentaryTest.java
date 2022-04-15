package com.project.mydraw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.project.mydraw.web.rest.TestUtil;

public class DisLikeCommentaryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisLikeCommentary.class);
        DisLikeCommentary disLikeCommentary1 = new DisLikeCommentary();
        disLikeCommentary1.setId(1L);
        DisLikeCommentary disLikeCommentary2 = new DisLikeCommentary();
        disLikeCommentary2.setId(disLikeCommentary1.getId());
        assertThat(disLikeCommentary1).isEqualTo(disLikeCommentary2);
        disLikeCommentary2.setId(2L);
        assertThat(disLikeCommentary1).isNotEqualTo(disLikeCommentary2);
        disLikeCommentary1.setId(null);
        assertThat(disLikeCommentary1).isNotEqualTo(disLikeCommentary2);
    }
}
