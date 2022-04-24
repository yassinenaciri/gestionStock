package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ResponsableProjet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResponsableProjet entity.
 */
@Repository
public interface ResponsableProjetRepository
    extends ResponsableProjetRepositoryWithBagRelationships, JpaRepository<ResponsableProjet, Long> {
    default Optional<ResponsableProjet> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ResponsableProjet> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<ResponsableProjet> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
