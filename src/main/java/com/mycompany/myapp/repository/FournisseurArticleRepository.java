package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FournisseurArticle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FournisseurArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournisseurArticleRepository extends JpaRepository<FournisseurArticle, Long> {}
