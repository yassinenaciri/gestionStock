package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ResponsableProjet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ResponsableProjetRepositoryWithBagRelationships {
    Optional<ResponsableProjet> fetchBagRelationships(Optional<ResponsableProjet> responsableProjet);

    List<ResponsableProjet> fetchBagRelationships(List<ResponsableProjet> responsableProjets);

    Page<ResponsableProjet> fetchBagRelationships(Page<ResponsableProjet> responsableProjets);
}
