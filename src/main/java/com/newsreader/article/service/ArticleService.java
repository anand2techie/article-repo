package com.newsreader.article.service;

import com.newsreader.article.model.ArticleDTO;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    Long createArticle(ArticleDTO articleDTO);

    ArticleDTO getArticleById(Long id);

    void updateArticleById(Long id, ArticleDTO articleDTO);

    void deleteArticleById(Long id);

    List<ArticleDTO> listArticles(Integer page, Integer pageSize, Map<String, String> viewParams);
}
