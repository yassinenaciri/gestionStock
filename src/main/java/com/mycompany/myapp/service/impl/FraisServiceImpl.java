package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Frais;
import com.mycompany.myapp.repository.FraisRepository;
import com.mycompany.myapp.service.FraisService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Frais}.
 */
@Service
@Transactional
public class FraisServiceImpl implements FraisService {

    private final Logger log = LoggerFactory.getLogger(FraisServiceImpl.class);

    private final FraisRepository fraisRepository;

    public FraisServiceImpl(FraisRepository fraisRepository) {
        this.fraisRepository = fraisRepository;
    }

    @Override
    public Frais save(Frais frais) {
        log.debug("Request to save Frais : {}", frais);
        return fraisRepository.save(frais);
    }

    @Override
    public Frais update(Frais frais) {
        log.debug("Request to save Frais : {}", frais);
        return fraisRepository.save(frais);
    }

    @Override
    public Optional<Frais> partialUpdate(Frais frais) {
        log.debug("Request to partially update Frais : {}", frais);

        return fraisRepository
            .findById(frais.getId())
            .map(existingFrais -> {
                if (frais.getPrix() != null) {
                    existingFrais.setPrix(frais.getPrix());
                }
                if (frais.getDesscription() != null) {
                    existingFrais.setDesscription(frais.getDesscription());
                }

                return existingFrais;
            })
            .map(fraisRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Frais> findAll(Pageable pageable) {
        log.debug("Request to get all Frais");
        return fraisRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Frais> findOne(Long id) {
        log.debug("Request to get Frais : {}", id);
        return fraisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Frais : {}", id);
        fraisRepository.deleteById(id);
    }
}
