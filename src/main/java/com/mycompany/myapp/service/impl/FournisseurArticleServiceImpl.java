package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FournisseurArticle;
import com.mycompany.myapp.repository.FournisseurArticleRepository;
import com.mycompany.myapp.service.FournisseurArticleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FournisseurArticle}.
 */
@Service
@Transactional
public class FournisseurArticleServiceImpl implements FournisseurArticleService {

    private final Logger log = LoggerFactory.getLogger(FournisseurArticleServiceImpl.class);

    private final FournisseurArticleRepository fournisseurArticleRepository;

    public FournisseurArticleServiceImpl(FournisseurArticleRepository fournisseurArticleRepository) {
        this.fournisseurArticleRepository = fournisseurArticleRepository;
    }

    @Override
    public FournisseurArticle save(FournisseurArticle fournisseurArticle) {
        log.debug("Request to save FournisseurArticle : {}", fournisseurArticle);
        return fournisseurArticleRepository.save(fournisseurArticle);
    }

    @Override
    public FournisseurArticle update(FournisseurArticle fournisseurArticle) {
        log.debug("Request to save FournisseurArticle : {}", fournisseurArticle);
        return fournisseurArticleRepository.save(fournisseurArticle);
    }

    @Override
    public Optional<FournisseurArticle> partialUpdate(FournisseurArticle fournisseurArticle) {
        log.debug("Request to partially update FournisseurArticle : {}", fournisseurArticle);

        return fournisseurArticleRepository
            .findById(fournisseurArticle.getId())
            .map(existingFournisseurArticle -> {
                if (fournisseurArticle.getPrix() != null) {
                    existingFournisseurArticle.setPrix(fournisseurArticle.getPrix());
                }

                return existingFournisseurArticle;
            })
            .map(fournisseurArticleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FournisseurArticle> findAll(Pageable pageable) {
        log.debug("Request to get all FournisseurArticles");
        return fournisseurArticleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FournisseurArticle> findOne(Long id) {
        log.debug("Request to get FournisseurArticle : {}", id);
        return fournisseurArticleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FournisseurArticle : {}", id);
        fournisseurArticleRepository.deleteById(id);
    }
}
