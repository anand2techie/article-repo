package com.newsreader.article.mapper;

import com.newsreader.article.entity.Article;
import com.newsreader.article.model.ArticleDTO;
import org.springframework.stereotype.Service;

@Service
public class ArticleDTO2EntityMapper {

    public Article from(ArticleDTO source) {
        Article article = new Article();
        map(source, article);
        return article;
    }

    public Article map(ArticleDTO source, Article target) {
        target.setHeader(source.getHeader());
        target.setAuthors(source.getAuthors());
        target.setKeywords(source.getKeywords());

        target.setPublishDate(source.getPublishDate());
        target.setShortDescription(source.getShortDescription());
        target.setText(source.getText());
        return target;
    }
}
