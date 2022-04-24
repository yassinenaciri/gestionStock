package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ArticleProjet;
import com.mycompany.myapp.repository.ArticleProjetRepository;
import com.mycompany.myapp.service.ArticleProjetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArticleProjet}.
 */
@Service
@Transactional
public class ArticleProjetServiceImpl implements ArticleProjetService {

    private final Logger log = LoggerFactory.getLogger(ArticleProjetServiceImpl.class);

    private final ArticleProjetRepository articleProjetRepository;

    public ArticleProjetServiceImpl(ArticleProjetRepository articleProjetRepository) {
        this.articleProjetRepository = articleProjetRepository;
    }

    @Override
    public ArticleProjet save(ArticleProjet articleProjet) {
        log.debug("Request to save ArticleProjet : {}", articleProjet);
        return articleProjetRepository.save(articleProjet);
    }

    @Override
    public ArticleProjet update(ArticleProjet articleProjet) {
        log.debug("Request to save ArticleProjet : {}", articleProjet);
        return articleProjetRepository.save(articleProjet);
    }

    @Override
    public Optional<ArticleProjet> partialUpdate(ArticleProjet articleProjet) {
        log.debug("Request to partially update ArticleProjet : {}", articleProjet);

        return articleProjetRepository
            .findById(articleProjet.getId())
            .map(existingArticleProjet -> {
                if (articleProjet.getNom() != null) {
                    existingArticleProjet.setNom(articleProjet.getNom());
                }
                if (articleProjet.getDescription() != null) {
                    existingArticleProjet.setDescription(articleProjet.getDescription());
                }
                if (articleProjet.getVendu() != null) {
                    existingArticleProjet.setVendu(articleProjet.getVendu());
                }

                return existingArticleProjet;
            })
            .map(articleProjetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleProjet> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleProjets");
        return articleProjetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleProjet> findOne(Long id) {
        log.debug("Request to get ArticleProjet : {}", id);
        return articleProjetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticleProjet : {}", id);
        articleProjetRepository.deleteById(id);
    }
}
