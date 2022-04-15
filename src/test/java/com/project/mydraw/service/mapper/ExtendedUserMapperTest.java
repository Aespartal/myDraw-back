package com.project.mydraw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedUserMapperTest {

    private ExtendedUserMapper extendedUserMapper;

    @BeforeEach
    public void setUp() {
        extendedUserMapper = new ExtendedUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(extendedUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(extendedUserMapper.fromId(null)).isNull();
    }
}
