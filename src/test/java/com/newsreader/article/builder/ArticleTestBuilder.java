package com.newsreader.article.builder;

import com.newsreader.article.model.ArticleDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleTestBuilder {

    private ArticleDTO articleDTO;

    ArticleTestBuilder() {
        articleDTO = new ArticleDTO();
    }

    public ArticleTestBuilder withArticleDetails(String header, String shortDescription, String text) {
        articleDTO.setHeader(header);
        articleDTO.setShortDescription(shortDescription);
        articleDTO.setText(text);
        return this;
    }

    public ArticleTestBuilder authoredBy(List<String> authors) {
        articleDTO.setAuthors(authors);
        return this;
    }

    public ArticleTestBuilder publishedOn(Date publishDate) {
        articleDTO.setPublishDate(publishDate);
        return this;
    }

    public ArticleTestBuilder hasKeywords(List<String> keywords) {
        articleDTO.setKeywords(keywords);
        return this;
    }

    public ArticleDTO build() {
        return articleDTO;
    }
}
