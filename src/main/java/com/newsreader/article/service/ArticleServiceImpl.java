package com.newsreader.article.service;

import com.newsreader.article.entity.Article;
import com.newsreader.article.exception.ArticleNotFoundException;
import com.newsreader.article.mapper.ArticleDTO2EntityMapper;
import com.newsreader.article.mapper.ArticleEntity2DTOMapper;
import com.newsreader.article.model.ArticleDTO;
import com.newsreader.article.repository.ArticleRepository;
import com.newsreader.article.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDTO2EntityMapper articleDTO2EntityMapper;

    @Autowired
    private ArticleEntity2DTOMapper articleEntity2DTOMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Long createArticle(ArticleDTO articleDTO) {
        //convert dto to entity
        Article article = articleDTO2EntityMapper.from(articleDTO);
        return articleRepository.save(article).getId();
    }

    @Override
    public ArticleDTO getArticleById(Long id) {
        Optional<Article> articleOptional = checkAndGetIfArticlePresent(id);

        //convert entity to dto
        return articleEntity2DTOMapper.from(articleOptional.get());
    }

    @Override
    public void updateArticleById(Long id, ArticleDTO articleDTO) {
        Optional<Article> articleOptional = checkAndGetIfArticlePresent(id);
        Article updatedArticle = articleDTO2EntityMapper.map(articleDTO, articleOptional.get());
        articleRepository.save(updatedArticle);
    }

    @Override
    public void deleteArticleById(Long id) {
        checkAndGetIfArticlePresent(id);
        articleRepository.deleteById(id);
    }

    private Optional<Article> checkAndGetIfArticlePresent(Long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (!articleOptional.isPresent()) {
            LOG.error("Article not found for the given id: {}", id);
            throw new ArticleNotFoundException("No article found");
        }
        return articleOptional;
    }

    @Override
    public List<ArticleDTO> listArticles(Integer page, Integer pageSize, Map<String, String> viewParams) {

        Date startDate = DateUtils.convertStringToDate(viewParams.get("startDate"));
        Date endDate = DateUtils.convertStringToDate(viewParams.get("endDate"));

        LOG.info(
            "List articles for the given criteria -> author: {}, keyword: {}, start date: {}, end date: {}, page: {}, "
                + "pageSize: {}",
            viewParams.get("author"), viewParams.get("keyword"), startDate, endDate, page, pageSize);
        return articleEntity2DTOMapper.from(articleRepository
            .findAllArticleByQuery(viewParams.get("author"), viewParams.get("keyword"), startDate, endDate,
                PageRequest.of(page, pageSize)));
    }

}
