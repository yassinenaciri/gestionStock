package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Commande;
import com.mycompany.myapp.repository.CommandeRepository;
import com.mycompany.myapp.service.CommandeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Commande}.
 */
@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {

    private final Logger log = LoggerFactory.getLogger(CommandeServiceImpl.class);

    private final CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Commande save(Commande commande) {
        log.debug("Request to save Commande : {}", commande);
        return commandeRepository.save(commande);
    }

    @Override
    public Commande update(Commande commande) {
        log.debug("Request to save Commande : {}", commande);
        return commandeRepository.save(commande);
    }

    @Override
    public Optional<Commande> partialUpdate(Commande commande) {
        log.debug("Request to partially update Commande : {}", commande);

        return commandeRepository
            .findById(commande.getId())
            .map(existingCommande -> {
                if (commande.getNom() != null) {
                    existingCommande.setNom(commande.getNom());
                }
                if (commande.getDescription() != null) {
                    existingCommande.setDescription(commande.getDescription());
                }
                if (commande.getRecu() != null) {
                    existingCommande.setRecu(commande.getRecu());
                }
                if (commande.getQuantite() != null) {
                    existingCommande.setQuantite(commande.getQuantite());
                }

                return existingCommande;
            })
            .map(commandeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Commande> findAll(Pageable pageable) {
        log.debug("Request to get all Commandes");
        return commandeRepository.findAll(pageable);
    }

    public Page<Commande> findAllWithEagerRelationships(Pageable pageable) {
        return commandeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Commande> findOne(Long id) {
        log.debug("Request to get Commande : {}", id);
        return commandeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commande : {}", id);
        commandeRepository.deleteById(id);
    }
}
