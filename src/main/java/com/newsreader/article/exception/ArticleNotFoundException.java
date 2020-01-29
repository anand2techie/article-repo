package com.newsreader.article.exception;

public class ArticleNotFoundException extends RuntimeException {

    private String message;

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
