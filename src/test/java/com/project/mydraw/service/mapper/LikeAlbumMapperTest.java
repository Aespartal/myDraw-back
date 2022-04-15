package com.project.mydraw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LikeAlbumMapperTest {

    private LikeAlbumMapper likeAlbumMapper;

    @BeforeEach
    public void setUp() {
        likeAlbumMapper = new LikeAlbumMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(likeAlbumMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(likeAlbumMapper.fromId(null)).isNull();
    }
}
