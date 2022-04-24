package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticleProjetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleProjet.class);
        ArticleProjet articleProjet1 = new ArticleProjet();
        articleProjet1.setId(1L);
        ArticleProjet articleProjet2 = new ArticleProjet();
        articleProjet2.setId(articleProjet1.getId());
        assertThat(articleProjet1).isEqualTo(articleProjet2);
        articleProjet2.setId(2L);
        assertThat(articleProjet1).isNotEqualTo(articleProjet2);
        articleProjet1.setId(null);
        assertThat(articleProjet1).isNotEqualTo(articleProjet2);
    }
}
