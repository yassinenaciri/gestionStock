package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SousAdminTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SousAdmin.class);
        SousAdmin sousAdmin1 = new SousAdmin();
        sousAdmin1.setId(1L);
        SousAdmin sousAdmin2 = new SousAdmin();
        sousAdmin2.setId(sousAdmin1.getId());
        assertThat(sousAdmin1).isEqualTo(sousAdmin2);
        sousAdmin2.setId(2L);
        assertThat(sousAdmin1).isNotEqualTo(sousAdmin2);
        sousAdmin1.setId(null);
        assertThat(sousAdmin1).isNotEqualTo(sousAdmin2);
    }
}
