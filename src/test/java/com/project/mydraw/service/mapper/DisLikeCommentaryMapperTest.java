package com.project.mydraw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DisLikeCommentaryMapperTest {

    private DisLikeCommentaryMapper disLikeCommentaryMapper;

    @BeforeEach
    public void setUp() {
        disLikeCommentaryMapper = new DisLikeCommentaryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(disLikeCommentaryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(disLikeCommentaryMapper.fromId(null)).isNull();
    }
}
