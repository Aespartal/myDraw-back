package com.project.mydraw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.project.mydraw.web.rest.TestUtil;

public class ExtendedUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtendedUserDTO.class);
        ExtendedUserDTO extendedUserDTO1 = new ExtendedUserDTO();
        extendedUserDTO1.setId(1L);
        ExtendedUserDTO extendedUserDTO2 = new ExtendedUserDTO();
        assertThat(extendedUserDTO1).isNotEqualTo(extendedUserDTO2);
        extendedUserDTO2.setId(extendedUserDTO1.getId());
        assertThat(extendedUserDTO1).isEqualTo(extendedUserDTO2);
        extendedUserDTO2.setId(2L);
        assertThat(extendedUserDTO1).isNotEqualTo(extendedUserDTO2);
        extendedUserDTO1.setId(null);
        assertThat(extendedUserDTO1).isNotEqualTo(extendedUserDTO2);
    }
}
