package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsableProjetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsableProjet.class);
        ResponsableProjet responsableProjet1 = new ResponsableProjet();
        responsableProjet1.setId(1L);
        ResponsableProjet responsableProjet2 = new ResponsableProjet();
        responsableProjet2.setId(responsableProjet1.getId());
        assertThat(responsableProjet1).isEqualTo(responsableProjet2);
        responsableProjet2.setId(2L);
        assertThat(responsableProjet1).isNotEqualTo(responsableProjet2);
        responsableProjet1.setId(null);
        assertThat(responsableProjet1).isNotEqualTo(responsableProjet2);
    }
}
