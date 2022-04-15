package com.project.mydraw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BackgroundColorMapperTest {

    private BackgroundColorMapper backgroundColorMapper;

    @BeforeEach
    public void setUp() {
        backgroundColorMapper = new BackgroundColorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(backgroundColorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(backgroundColorMapper.fromId(null)).isNull();
    }
}
