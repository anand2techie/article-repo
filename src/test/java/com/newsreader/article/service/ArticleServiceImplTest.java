package com.newsreader.article.service;

import com.newsreader.article.exception.ArticleNotFoundException;
import com.newsreader.article.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceImplTest {

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test(expected = ArticleNotFoundException.class)
    public void testGetArticleByIdIfNotPresent() {
        Mockito.when(articleRepository.findById(1l)).thenReturn(Optional.empty());
        articleService.getArticleById(1l);
    }
}
