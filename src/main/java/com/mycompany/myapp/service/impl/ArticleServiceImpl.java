package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Article;
import com.mycompany.myapp.repository.ArticleRepository;
import com.mycompany.myapp.service.ArticleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Article}.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        log.debug("Request to save Article : {}", article);
        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
        log.debug("Request to save Article : {}", article);
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> partialUpdate(Article article) {
        log.debug("Request to partially update Article : {}", article);

        return articleRepository
            .findById(article.getId())
            .map(existingArticle -> {
                if (article.getNom() != null) {
                    existingArticle.setNom(article.getNom());
                }
                if (article.getDesciption() != null) {
                    existingArticle.setDesciption(article.getDesciption());
                }

                return existingArticle;
            })
            .map(articleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Article> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
