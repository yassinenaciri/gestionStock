package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ResponsableProjet;
import com.mycompany.myapp.repository.ResponsableProjetRepository;
import com.mycompany.myapp.service.ResponsableProjetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResponsableProjet}.
 */
@Service
@Transactional
public class ResponsableProjetServiceImpl implements ResponsableProjetService {

    private final Logger log = LoggerFactory.getLogger(ResponsableProjetServiceImpl.class);

    private final ResponsableProjetRepository responsableProjetRepository;

    public ResponsableProjetServiceImpl(ResponsableProjetRepository responsableProjetRepository) {
        this.responsableProjetRepository = responsableProjetRepository;
    }

    @Override
    public ResponsableProjet save(ResponsableProjet responsableProjet) {
        log.debug("Request to save ResponsableProjet : {}", responsableProjet);
        return responsableProjetRepository.save(responsableProjet);
    }

    @Override
    public ResponsableProjet update(ResponsableProjet responsableProjet) {
        log.debug("Request to save ResponsableProjet : {}", responsableProjet);
        return responsableProjetRepository.save(responsableProjet);
    }

    @Override
    public Optional<ResponsableProjet> partialUpdate(ResponsableProjet responsableProjet) {
        log.debug("Request to partially update ResponsableProjet : {}", responsableProjet);

        return responsableProjetRepository
            .findById(responsableProjet.getId())
            .map(existingResponsableProjet -> {
                if (responsableProjet.getNom() != null) {
                    existingResponsableProjet.setNom(responsableProjet.getNom());
                }
                if (responsableProjet.getPrenom() != null) {
                    existingResponsableProjet.setPrenom(responsableProjet.getPrenom());
                }
                if (responsableProjet.getMail() != null) {
                    existingResponsableProjet.setMail(responsableProjet.getMail());
                }

                return existingResponsableProjet;
            })
            .map(responsableProjetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsableProjet> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsableProjets");
        return responsableProjetRepository.findAll(pageable);
    }

    public Page<ResponsableProjet> findAllWithEagerRelationships(Pageable pageable) {
        return responsableProjetRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsableProjet> findOne(Long id) {
        log.debug("Request to get ResponsableProjet : {}", id);
        return responsableProjetRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponsableProjet : {}", id);
        responsableProjetRepository.deleteById(id);
    }
}
