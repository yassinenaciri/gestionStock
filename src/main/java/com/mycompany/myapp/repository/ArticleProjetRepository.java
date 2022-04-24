package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ArticleProjet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ArticleProjet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleProjetRepository extends JpaRepository<ArticleProjet, Long> {}
