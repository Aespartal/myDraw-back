package com.project.mydraw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.project.mydraw.web.rest.TestUtil;

public class CommentaryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentaryDTO.class);
        CommentaryDTO commentaryDTO1 = new CommentaryDTO();
        commentaryDTO1.setId(1L);
        CommentaryDTO commentaryDTO2 = new CommentaryDTO();
        assertThat(commentaryDTO1).isNotEqualTo(commentaryDTO2);
        commentaryDTO2.setId(commentaryDTO1.getId());
        assertThat(commentaryDTO1).isEqualTo(commentaryDTO2);
        commentaryDTO2.setId(2L);
        assertThat(commentaryDTO1).isNotEqualTo(commentaryDTO2);
        commentaryDTO1.setId(null);
        assertThat(commentaryDTO1).isNotEqualTo(commentaryDTO2);
    }
}
