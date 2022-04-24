package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Entreprise;
import com.mycompany.myapp.repository.EntrepriseRepository;
import com.mycompany.myapp.service.EntrepriseService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Entreprise}.
 */
@Service
@Transactional
public class EntrepriseServiceImpl implements EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public Entreprise save(Entreprise entreprise) {
        log.debug("Request to save Entreprise : {}", entreprise);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Entreprise update(Entreprise entreprise) {
        log.debug("Request to save Entreprise : {}", entreprise);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Optional<Entreprise> partialUpdate(Entreprise entreprise) {
        log.debug("Request to partially update Entreprise : {}", entreprise);

        return entrepriseRepository
            .findById(entreprise.getId())
            .map(existingEntreprise -> {
                if (entreprise.getNom() != null) {
                    existingEntreprise.setNom(entreprise.getNom());
                }

                return existingEntreprise;
            })
            .map(entrepriseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Entreprise> findAll(Pageable pageable) {
        log.debug("Request to get all Entreprises");
        return entrepriseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entreprise> findOne(Long id) {
        log.debug("Request to get Entreprise : {}", id);
        return entrepriseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
    }
}
