package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FournisseurArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FournisseurArticle.class);
        FournisseurArticle fournisseurArticle1 = new FournisseurArticle();
        fournisseurArticle1.setId(1L);
        FournisseurArticle fournisseurArticle2 = new FournisseurArticle();
        fournisseurArticle2.setId(fournisseurArticle1.getId());
        assertThat(fournisseurArticle1).isEqualTo(fournisseurArticle2);
        fournisseurArticle2.setId(2L);
        assertThat(fournisseurArticle1).isNotEqualTo(fournisseurArticle2);
        fournisseurArticle1.setId(null);
        assertThat(fournisseurArticle1).isNotEqualTo(fournisseurArticle2);
    }
}
