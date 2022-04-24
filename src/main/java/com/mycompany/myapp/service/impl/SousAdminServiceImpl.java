package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.SousAdmin;
import com.mycompany.myapp.repository.SousAdminRepository;
import com.mycompany.myapp.service.SousAdminService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SousAdmin}.
 */
@Service
@Transactional
public class SousAdminServiceImpl implements SousAdminService {

    private final Logger log = LoggerFactory.getLogger(SousAdminServiceImpl.class);

    private final SousAdminRepository sousAdminRepository;

    public SousAdminServiceImpl(SousAdminRepository sousAdminRepository) {
        this.sousAdminRepository = sousAdminRepository;
    }

    @Override
    public SousAdmin save(SousAdmin sousAdmin) {
        log.debug("Request to save SousAdmin : {}", sousAdmin);
        return sousAdminRepository.save(sousAdmin);
    }

    @Override
    public SousAdmin update(SousAdmin sousAdmin) {
        log.debug("Request to save SousAdmin : {}", sousAdmin);
        return sousAdminRepository.save(sousAdmin);
    }

    @Override
    public Optional<SousAdmin> partialUpdate(SousAdmin sousAdmin) {
        log.debug("Request to partially update SousAdmin : {}", sousAdmin);

        return sousAdminRepository
            .findById(sousAdmin.getId())
            .map(existingSousAdmin -> {
                if (sousAdmin.getNom() != null) {
                    existingSousAdmin.setNom(sousAdmin.getNom());
                }
                if (sousAdmin.getPrenom() != null) {
                    existingSousAdmin.setPrenom(sousAdmin.getPrenom());
                }
                if (sousAdmin.getMail() != null) {
                    existingSousAdmin.setMail(sousAdmin.getMail());
                }

                return existingSousAdmin;
            })
            .map(sousAdminRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SousAdmin> findAll(Pageable pageable) {
        log.debug("Request to get all SousAdmins");
        return sousAdminRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SousAdmin> findOne(Long id) {
        log.debug("Request to get SousAdmin : {}", id);
        return sousAdminRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SousAdmin : {}", id);
        sousAdminRepository.deleteById(id);
    }
}
