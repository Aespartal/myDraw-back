package com.project.mydraw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.project.mydraw.web.rest.TestUtil;

public class DisLikeCommentaryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisLikeCommentaryDTO.class);
        DisLikeCommentaryDTO disLikeCommentaryDTO1 = new DisLikeCommentaryDTO();
        disLikeCommentaryDTO1.setId(1L);
        DisLikeCommentaryDTO disLikeCommentaryDTO2 = new DisLikeCommentaryDTO();
        assertThat(disLikeCommentaryDTO1).isNotEqualTo(disLikeCommentaryDTO2);
        disLikeCommentaryDTO2.setId(disLikeCommentaryDTO1.getId());
        assertThat(disLikeCommentaryDTO1).isEqualTo(disLikeCommentaryDTO2);
        disLikeCommentaryDTO2.setId(2L);
        assertThat(disLikeCommentaryDTO1).isNotEqualTo(disLikeCommentaryDTO2);
        disLikeCommentaryDTO1.setId(null);
        assertThat(disLikeCommentaryDTO1).isNotEqualTo(disLikeCommentaryDTO2);
    }
}
