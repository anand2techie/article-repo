package com.newsreader.article.mapper;

import com.newsreader.article.entity.Article;
import com.newsreader.article.model.ArticleDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleEntity2DTOMapper {

    public ArticleDTO from(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setKeywords(article.getKeywords());
        articleDTO.setAuthors(article.getAuthors());
        articleDTO.setHeader(article.getHeader());
        articleDTO.setPublishDate(article.getPublishDate());
        articleDTO.setShortDescription(article.getShortDescription());
        articleDTO.setText(article.getText());

        return articleDTO;
    }

    public List<ArticleDTO> from(List<Article> articles) {
        return articles.stream().map(this::from).collect(Collectors.toList());
    }
}
