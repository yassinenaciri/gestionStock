package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Paiement;
import com.mycompany.myapp.repository.PaiementRepository;
import com.mycompany.myapp.service.PaiementService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Paiement}.
 */
@Service
@Transactional
public class PaiementServiceImpl implements PaiementService {

    private final Logger log = LoggerFactory.getLogger(PaiementServiceImpl.class);

    private final PaiementRepository paiementRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    @Override
    public Paiement save(Paiement paiement) {
        log.debug("Request to save Paiement : {}", paiement);
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement update(Paiement paiement) {
        log.debug("Request to save Paiement : {}", paiement);
        return paiementRepository.save(paiement);
    }

    @Override
    public Optional<Paiement> partialUpdate(Paiement paiement) {
        log.debug("Request to partially update Paiement : {}", paiement);

        return paiementRepository
            .findById(paiement.getId())
            .map(existingPaiement -> {
                if (paiement.getNom() != null) {
                    existingPaiement.setNom(paiement.getNom());
                }
                if (paiement.getPrenom() != null) {
                    existingPaiement.setPrenom(paiement.getPrenom());
                }
                if (paiement.getMail() != null) {
                    existingPaiement.setMail(paiement.getMail());
                }

                return existingPaiement;
            })
            .map(paiementRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paiement> findAll(Pageable pageable) {
        log.debug("Request to get all Paiements");
        return paiementRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paiement> findOne(Long id) {
        log.debug("Request to get Paiement : {}", id);
        return paiementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paiement : {}", id);
        paiementRepository.deleteById(id);
    }
}
