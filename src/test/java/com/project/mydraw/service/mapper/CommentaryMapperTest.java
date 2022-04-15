package com.project.mydraw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentaryMapperTest {

    private CommentaryMapper commentaryMapper;

    @BeforeEach
    public void setUp() {
        commentaryMapper = new CommentaryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commentaryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commentaryMapper.fromId(null)).isNull();
    }
}
