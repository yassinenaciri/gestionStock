package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Projet;
import com.mycompany.myapp.repository.ProjetRepository;
import com.mycompany.myapp.service.ProjetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Projet}.
 */
@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {

    private final Logger log = LoggerFactory.getLogger(ProjetServiceImpl.class);

    private final ProjetRepository projetRepository;

    public ProjetServiceImpl(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    @Override
    public Projet save(Projet projet) {
        log.debug("Request to save Projet : {}", projet);
        return projetRepository.save(projet);
    }

    @Override
    public Projet update(Projet projet) {
        log.debug("Request to save Projet : {}", projet);
        return projetRepository.save(projet);
    }

    @Override
    public Optional<Projet> partialUpdate(Projet projet) {
        log.debug("Request to partially update Projet : {}", projet);

        return projetRepository
            .findById(projet.getId())
            .map(existingProjet -> {
                if (projet.getNom() != null) {
                    existingProjet.setNom(projet.getNom());
                }
                if (projet.getDescription() != null) {
                    existingProjet.setDescription(projet.getDescription());
                }

                return existingProjet;
            })
            .map(projetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findAll(Pageable pageable) {
        log.debug("Request to get all Projets");
        return projetRepository.findAll(pageable);
    }

    public Page<Projet> findAllWithEagerRelationships(Pageable pageable) {
        return projetRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Projet> findOne(Long id) {
        log.debug("Request to get Projet : {}", id);
        return projetRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Projet : {}", id);
        projetRepository.deleteById(id);
    }
}
