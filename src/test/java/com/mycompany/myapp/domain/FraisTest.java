package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Frais.class);
        Frais frais1 = new Frais();
        frais1.setId(1L);
        Frais frais2 = new Frais();
        frais2.setId(frais1.getId());
        assertThat(frais1).isEqualTo(frais2);
        frais2.setId(2L);
        assertThat(frais1).isNotEqualTo(frais2);
        frais1.setId(null);
        assertThat(frais1).isNotEqualTo(frais2);
    }
}
