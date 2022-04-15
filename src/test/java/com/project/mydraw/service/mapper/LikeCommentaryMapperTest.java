package com.project.mydraw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LikeCommentaryMapperTest {

    private LikeCommentaryMapper likeCommentaryMapper;

    @BeforeEach
    public void setUp() {
        likeCommentaryMapper = new LikeCommentaryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(likeCommentaryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(likeCommentaryMapper.fromId(null)).isNull();
    }
}
